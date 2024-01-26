import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './User';
import { UserService } from './UserService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit  {
  constructor(private userService: UserService ,private router: Router) { }
  user: User = new User();
  username: any;
  password: any;
  errorMessage : any;
  currentUser: any;

  ngOnInit() {



    const script = document.createElement('script');
    script.src = '../../../assets/js/login.js';
    script.type = 'text/javascript';
    document.body.appendChild(script);



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
  }



  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    this.user.profile_pic = file;
}


  onSubmit() {
    this.userService.addUser(this.user)
      .subscribe(() => {
        alert('User added successfully!');
      });
  }

  onLoginSubmit() {
    this.errorMessage = '';
    this.userService.login(this.username, this.password).subscribe(
      user => {
        sessionStorage.setItem('currentUser', JSON.stringify(user));
        sessionStorage.setItem('userId', user.id); // add this line to store the user ID

        alert('Login successful!');
        this.router.navigate(['/homelogged']);
      },
      error => {
        console.log('Error response:', error);
        if (error.status === 401) {
          this.errorMessage = 'Invalid username or password'; // Set the error message based on the error response
          alert('Invalid username or password'); // Print an alert if the error response is a 401 Unauthorized error
        } else {
          this.errorMessage = error;
        }
        this.router.navigate(['/login']);
      }
    );
  }








}
