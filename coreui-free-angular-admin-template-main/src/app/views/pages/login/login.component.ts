import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: any = {
    userName: null,
    password: null
  };
  errorMsg=''
  islogin=true;

  public loginForm!: FormGroup;

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private fb: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.onCreateForm();
  }

  // login() {
  //   console.log(this.loginForm.value)
  //   this.authService.login("", "").subscribe((data) => {
  //     // If valid and route to card
  //     if (data) {
  //       this.router.navigate(['/dashboard']);  // If valid and route to card
  //     }
  //     //this.isSubmitted = true;
  //     // this.isValidUser = data; // false show error message
  //   });
  // };

  login() {
    console.log(this.loginForm.value)
    this.userService.signin(this.loginForm.value).subscribe(
      data => {
        this.islogin=true;
        console.log("success");
        this.authService.login(this.loginForm.value);
        this.router.navigate(['/dashboard']);
        console.log(data)
      },
      err => {
        this.islogin=false;
        console.log("error");
        console.log(err.error)
        this.errorMsg=err.error.message;
       
      }
    );
  };

  private onCreateForm = (): void => {
    this.loginForm = this.fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });
  };
}
