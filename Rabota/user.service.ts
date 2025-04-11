import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'https://reqres.in/api';

  constructor(private http: HttpClient) {}

  getUsers(page: number): Observable<any> {
    return this.http.get(${this.baseUrl}/users?page=${page});
  }

  getUser(id: number): Observable<any> {
    return this.http.get(${this.baseUrl}/users/${id});
  }

  updateUser(id: number, userData: any): Observable<any> {
    return this.http.put(${this.baseUrl}/users/${id}, userData);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(${this.baseUrl}/users/${id});
  }

  getResources(): Observable<any> {
    return this.http.get(${this.baseUrl}/unknown);
  }
}
login(credentials: any): Observable<any> {
  return this.http.post(${this.baseUrl}/login, credentials);
}

register(credentials: any): Observable<any> {
  return this.http.post(${this.baseUrl}/register, credentials);
}
