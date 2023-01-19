import { Component,OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-forgot-psw',
  templateUrl: './forgot-psw.component.html',
  styleUrls: ['./forgot-psw.component.scss']
})
export class ForgotPswComponent implements OnInit {

  errorMsg=''
  islogin=true;

  public tempPswForm!: FormGroup;

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private fb: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.onCreateForm();
  }

  sendTempPsw() {
    console.log(this.tempPswForm.value)
    this.userService.sendTempPsw(this.tempPswForm.value).subscribe(
      data => {
        this.islogin=true;
        console.log("success");
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
    this.tempPswForm = this.fb.group({
      userName: [null, [Validators.required]],
      email: [null, [Validators.required]],
      phone: [null, [Validators.required]],
      dob: [null, [Validators.required]],
    });
  };
}

