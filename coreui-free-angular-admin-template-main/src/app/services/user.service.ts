import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

const API_URL = 'http://localhost:8081/api/user/';
const AUDIT_API_URL = 'http://localhost:8081/api/audit/';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  

  constructor(private http: HttpClient) { }

  signin(value: any): Observable<any> {
    return this.http.post(API_URL + 'signin', value, { responseType: 'json' });
  }

  getUser(value: any): Observable<any> {
    return this.http.get(API_URL + 'get/username?userName=' + value, { responseType: 'json' });
  }

  updateUser(value: any) {
    return this.http.put(API_URL + 'update/profile', value, { responseType: 'json' });
  }

  loadProfile(value: any) {
    return this.http.get(API_URL + 'get/profile/username?userName=' + value, { responseType: 'json' });
  }

  registerUser(value: any) {
    return this.http.post(API_URL + 'register', value, { responseType: 'json' });
  }

  changePassword(value: any) {
    return this.http.put(API_URL + 'change/psw', value, { responseType: 'json' });
  }

  search(value: any) {
    let urlParam = '';
    if (value.userName != null && value.userName != '') {
      urlParam += urlParam.length > 0 ? "&userName=" + value.userName : "?userName=" + value.userName;
    }
    if (value.firstName != null && value.firstName != '') {
      urlParam += urlParam.length > 0 ? "&firstName=" + value.firstName : "?firstName=" + value.firstName;
    }
    if (value.lastName != null && value.lastName != '') {
      urlParam += urlParam.length > 0 ? "&lastName=" + value.lastName : "?lastName=" + value.lastName;
    }
    if (value.dob != null) {
      urlParam += urlParam.length > 0 ? "&dob=" + value.dob : "?dob=" + value.dob;
    }
    return this.http.get(API_URL + 'search' + urlParam, { responseType: 'json' });
  }

  sendTempPsw(value: any) {
    return this.http.put(API_URL + 'send/temp/psw', value, { responseType: 'json' });
  }


  downloadAudit(userName: any) {
    return this.http.get(AUDIT_API_URL+"download?userName="+userName, {              
      responseType: 'blob'
    });
  }

}
