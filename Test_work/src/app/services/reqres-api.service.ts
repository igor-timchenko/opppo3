import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ListResponse, Resource, SingleResponse, UpdateUserPayload, UpdateUserResponse, User, LoginPayload, AuthResponse } from '../models';
import { Observable } from 'rxjs';

const BASE_URL = 'https://reqres.in/api';

@Injectable({ providedIn: 'root' })
export class ReqresApiService {
  constructor(private http: HttpClient) {}

  getUsers(page = 2): Observable<ListResponse<User>> {
    return this.http.get<ListResponse<User>>(`${BASE_URL}/users?page=${page}`);
    // на reqres пагинация фиксирована, используем page=2 по ТЗ
  }

  getUser(id: number): Observable<SingleResponse<User>> {
    return this.http.get<SingleResponse<User>>(`${BASE_URL}/users/${id}`);
  }

  updateUser(id: number, payload: UpdateUserPayload): Observable<UpdateUserResponse> {
    return this.http.put<UpdateUserResponse>(`${BASE_URL}/users/${id}`, payload);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${BASE_URL}/users/${id}`);
  }

  getResources(): Observable<ListResponse<Resource>> {
    return this.http.get<ListResponse<Resource>>(`${BASE_URL}/unknown`);
  }

  // необязательно
  login(payload: LoginPayload): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${BASE_URL}/login`, payload);
  }

  register(payload: LoginPayload): Observable<AuthResponse> { // reqres требует email+password
    return this.http.post<AuthResponse>(`${BASE_URL}/register`, payload);
  }
}
