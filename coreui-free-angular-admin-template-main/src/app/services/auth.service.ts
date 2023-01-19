import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
// import { HttpClient } from '@angular/common/http';

const USER_KEY = 'auth-user';
@Injectable({
  providedIn: 'root',
})
export class AuthService {

  // constructor(private http: HttpClient) { }

 
  isAuthenticate: boolean = false;

  // signin(value: any): Observable<any> {
  //   this.isAuthenticate = true;
  //   return "this.http.get(API_URL + 'signin',value)";
  // }

  login(user:any): Observable<boolean> {
      this.isAuthenticate = true;
      window.sessionStorage.removeItem(USER_KEY);
      window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
      return of(true);
    
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return {};
  }

  logout(){
    this.isAuthenticate = false;
    window.sessionStorage.clear();
    return of(false);
  }
}