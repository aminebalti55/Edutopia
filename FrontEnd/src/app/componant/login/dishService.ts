import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Dish } from './dish';
import { catchError } from 'rxjs/operators';
import { Menu } from './menu';

@Injectable({
  providedIn: 'root'
})
export class DishService {
private apiUrl = 'http://localhost:8086';

constructor(private http: HttpClient) { }
addDish(dish: Dish): Observable<any> {
  const formData = new FormData();
  formData.append('name', dish.name);
  formData.append('description', dish.description);
  formData.append('price', dish.price);
  if (dish.image) {
    formData.append('image', dish.image);
  }
  formData.append('category', dish.category);
  formData.append('spicinessLevel', dish.spicinessLevel);

  return this.http.post<any>(`${this.apiUrl}/Edutopia/dishes/addDish`, formData);
}

updateDish(dish: Dish , dishId:number) {
  const formData = new FormData();
  formData.append('name', dish.name);
  formData.append('description', dish.description);
  formData.append('price', dish.price.toString());
  if (dish.image) {
    formData.append('image', dish.image);
  }
  formData.append('category', dish.category);
  formData.append('spicinessLevel', dish.spicinessLevel);

  const url = `http://localhost:8086/Edutopia/dishes/Update/${dishId}`;
  return this.http.put<Dish>(url, formData);
}



getDishById(dishId: number): Observable<Dish> {
  const url = `${this.apiUrl}/Edutopia/dishes/getdishbyid/${dishId}`;
  return this.http.get<Dish>(url);
}


getAllDishes(): Observable<Dish[]> {
  return this.http.get<Dish[]>(`${this.apiUrl}/Edutopia/dishes/Getalldishes`);
}


updateMenu(): Observable<Menu> {
  return this.http.post<Menu>(`${this.apiUrl}/Edutopia/menus/update`, null);
}

getMenuWithDishes(id: number): Observable<Menu> {
  return this.http.get<Menu>(`${this.apiUrl}/Edutopia/menus/dishes/${id}`);
}

getAllMenus(): Observable<Menu[]> {
  return this.http.get<Menu[]>(`${this.apiUrl}/Edutopia/menus/getallmenus`);
}


getTop3HighestRatedDishes(): Observable<Dish[]> {
  const url = `${this.apiUrl}/Edutopia/dishes/top3HighestRated`;
  return this.http.get<Dish[]>(url);
}

getRecommendedDishes(username: string): Observable<Dish[]> {
  const url = `${this.apiUrl}/Edutopia/dishes/${username}`;
  return this.http.get<Dish[]>(url);
}

}
