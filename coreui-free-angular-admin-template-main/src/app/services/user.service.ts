import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

const API_URL = 'http://localhost:8081/api/user/';

@Injectable({
  providedIn: 'root'
})
export class UserService {
   
  constructor(private http: HttpClient) { }

  signin(value:any): Observable<any> {
    return this.http.post(API_URL + 'signin',value, { responseType: 'json' });
  }

  getUser(value:any): Observable<any> {
    return this.http.get(API_URL + 'get/username?userName='+value, { responseType: 'json' });
  }

  updateUser(value: any) {
    return this.http.put(API_URL + 'update/profile',value, { responseType: 'json' });
  }

  loadProfile(value: any) {
    return this.http.get(API_URL + 'get/profile/username?userName='+value, { responseType: 'json' });
  }

  registerUser(value: any) {
    return this.http.post(API_URL + 'register',value, { responseType: 'json' });
  }

}
