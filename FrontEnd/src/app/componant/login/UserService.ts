import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './User';
import { Subscription } from './subscription';

@Injectable({
  providedIn: 'root'
})


export class UserService {


  private apiUrl = 'http://localhost:8086';

  constructor(private http: HttpClient) { }

  addUser(user: User): Observable<any> {
    const formData = new FormData();
    formData.append('firstname', user.firstname);
    formData.append('lastname', user.lastname);
    formData.append('username', user.username);
    formData.append('email', user.email);
    formData.append('password', user.password);
    formData.append('gender', user.gender);
    formData.append('spicinessTolerance', user.spicinessTolerance);

    if (user.profile_pic) {
      formData.append('profilePic', user.profile_pic, user.profile_pic.name);
  }
    return this.http.post<any>(`${this.apiUrl}/Edutopia/user/ajouter`, formData);
  }

  login(username: string, password: string): Observable<User> {
    const loginRequest = { username, password };
    return this.http.post<User>(`${this.apiUrl}/Edutopia/user/login`, loginRequest);
  }


  getProfilePicture(userId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/Edutopia/user/profile-picture/${userId}`, { responseType: 'blob' });
  }

  getUserByid (userId: number): Observable<User> {
    const url = `${this.apiUrl}/Edutopia/user/getUserById/${userId}`;
    return this.http.get<User>(url);
  }

  logout() {
    return this.http.get(`${this.apiUrl}/Edutopia/user/logout`, { responseType: 'text' });
  }

  purchaseSubscription(userId: number, packageType: string, endDate: Date): Observable<Subscription> {
    const url = `${this.apiUrl}/Edutopia/subscriptions/Purchase?userId=${userId}&packageType=${packageType}&endDate=${endDate}`;
    return this.http.post<Subscription>(url, null);
  }

}
