import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rating } from './rating';

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private apiUrl = 'http://localhost:8086';

  constructor(private http: HttpClient) { }

  rateDish(userId: number, dishId: number, score: number): Observable<Rating> {
    const url = `${this.apiUrl}/Edutopia/ratings/rate/${dishId}?userId=${userId}&score=${score}`;
    return this.http.post<Rating>(url, null);
  }
}
