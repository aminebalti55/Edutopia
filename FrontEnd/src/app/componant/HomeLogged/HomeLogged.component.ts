import { Component, OnInit } from '@angular/core';
import { User } from '../login/User';
import { UserService } from '../login/UserService';
import { Router } from '@angular/router';
import { Dish } from '../login/dish';
import { DishService } from '../login/dishService';
import { Rating } from '../login/rating';
import { RatingService } from '../login/ratingService';
import { HttpErrorResponse } from '@angular/common/http';
import { Menu } from '../login/menu';
import { PackageType } from '../login/packagetype';
import { ViewChild, ElementRef } from '@angular/core';

import { Subscription } from '../login/subscription';

@Component({
  selector: 'app-HomeLogged',
  templateUrl: './HomeLogged.component.html',
  styleUrls: ['./HomeLogged.component.css']
})
export class HomeLoggedComponent implements OnInit {
  @ViewChild('endDateInput') endDateInput!: ElementRef<HTMLInputElement>;

  constructor(private userService: UserService,private router: Router,private dishService: DishService,private ratingService: RatingService) { }
  user: User = new User();
  dishesMenu: Dish[] = [];

  dishes: Dish[] = [];
  didi: Dish[] = [];
  recommendedDishes: Dish[] = [];

  dish: Dish = new Dish();
  currentUser: any;
  profilePicture: any;
  ratingScore = 0;
  menu: Menu = new Menu();
  menus: Menu[] = [];
  subscription: Subscription = new Subscription();
  packageType = PackageType; // Add the PackageType enum
  endDate: any;


  ngOnInit() {
    const myuser  = JSON.parse(sessionStorage.getItem('currentUser') || '{}');
    console.log('currentUser:', myuser);
    const username = myuser.user.username

    this.dishService.getRecommendedDishes(username).subscribe((recommendedDishes: Dish[]) => {
      this.recommendedDishes = recommendedDishes;
    });


    this.dishService.getTop3HighestRatedDishes().subscribe(
      (didi: Dish[]) => {
        // Assign the fetched dishes to the dishes array
        this.didi = didi

      },

      (error) => {
        console.log(error);
      }
    );


    this.getDishes();





    this.dishService.getAllMenus().subscribe(
      (menus: Menu[]) => {
        this.menus = menus;

        // Get the first menu in the list and fetch its dishes
        const menuId = menus[0].menuId;
        this.dishService.getMenuWithDishes(menuId).subscribe(
          (menu: Menu) => {
            this.menu = menu;
          },
          (error) => {
            console.log(error);
          }
        );
      },
      (error) => {
        console.log(error);
      }
    );

    this.currentUser = sessionStorage.getItem('currentUser');

    if (this.currentUser) {
      // The user session exists in the browser.
      // You can do something here, such as displaying a welcome message or
      // redirecting to the user's dashboard.
      console.log('User session exists:', this.currentUser);
    } else {
      // The user session does not exist in the browser.
      // You can do something here, such as displaying a login form or
      // redirecting to the login page.
      console.log('User session does not exist.');
    }

    const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');
    console.log('currentUser:', currentUser);
    const userId = currentUser.user.userId
;
    console.log('userId:', userId);

    this.userService.getUserByid(userId).subscribe(
      user => {
        this.user = user;
        console.log('user:', user);
        if (user.profile_pic) {
          this.userService.getProfilePicture(userId).subscribe(
            profilePicture => {
              this.profilePicture = URL.createObjectURL(profilePicture);
            },
            error => {
              console.log('Error fetching profile picture:', error);
            }
          );
        }
      },
      error => {
        console.log('Error fetching user:', error);
      }
    );

    this.updateMenu();



  }

  purchaseSubscription(packageType: PackageType) {
    const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');
    const userId = currentUser.user.userId;
    const endDate = this.subscription.endDate; // Get the selected end date from the subscription object

    this.userService
      .purchaseSubscription(userId, packageType, endDate)
      .subscribe((subscription: Subscription) => {
        this.subscription = subscription;
        // Handle success
        window.alert('Subscription purchased successfully!');
      }, error => {
        // Handle error
        window.alert('Error purchasing subscription. Please try again later.');
      });
  }



  updateMenu() {
    this.dishService.updateMenu().subscribe(
      response => {
        this.menu = response;
        console.log(this.menu);
      },
      error => {
        console.error(error);
      }
    );
  }

  rateDish(dish: Dish, score: number) {
    const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');
    const userId = currentUser.user.userId;
    this.ratingService.rateDish(userId, dish.dishId, score).subscribe(
      (rating: Rating) => {
        dish.score = rating.score;
        alert('Your rating has been submitted successfully!');
      },
      (error: HttpErrorResponse) => {
        alert('Your rating has been submitted successfully!');
      }
    );
  }

  getDish(dishId: number): void {
    this.dishService.getDishById(dishId).subscribe(
      dish => {
        // set the dish data in the component
        this.dish = dish;
      },
      error => {
        // handle error
        console.error('Error getting dish:', error);
      }
    );
  }
  onLogout(event: Event) {
    event.preventDefault();
    console.log('Logout button clicked'); // Add a console log statement here to confirm that the method is being called
    this.userService.logout().subscribe(
      (response) => {
        console.log(response); // Add a console log statement here to check the response from the server
        sessionStorage.removeItem('currentUser');
        this.router.navigate(['/login']);
      },
      (error) => {
        console.log(error); // Add a console log statement here to check for any errors
      }
    );
  }

  getDishes(): void {
    this.dishService.getAllDishes().subscribe(dishes => this.dishes = dishes);
  }


}

