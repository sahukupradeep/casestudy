import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  errorMsg = ''
  islogin = true;

  public loginForm!: FormGroup;
  submitted = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private fb: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.onCreateForm();
  }
  login() {
    //console.log(this.loginForm.value)
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }
    this.userService.signin(this.loginForm.value).subscribe(
      data => {
        this.islogin = true;
        console.log("success");
        // 
        this.authService.login(this.loginForm.value, data.message);
        this.loadUser(this.loginForm.value.userName);
        // this.router.navigate(['/dashboard']);
       // console.log(data)
      },
      err => {
        this.islogin = false;
        console.log("error");
        console.log(err.error)
        this.errorMsg = err.error.message;

      }
    );
  };

  private loadUser(userName: any) {
    console.log({userName});
    this.userService.getUser(userName).subscribe(data => {
        console.log("success "+data?.roleId);
        // console.log(data)
        if(data?.roleId!=null && data?.roleId==1){
          this.authService.role="admin";
        } else{
          this.authService.role=null; 
        }

        // Go to dashboard
        this.gotoDashboard();
      },
      err => {
        console.log("error");
        console.log(err.error);
      }
    );
  }

  private gotoDashboard = () => {
    this.router.navigate(['/dashboard']);
  };

  private onCreateForm = (): void => {
    this.loginForm = this.fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });
  };

  get f(): { [key: string]: AbstractControl } {
    return this.loginForm.controls;
  }
}
