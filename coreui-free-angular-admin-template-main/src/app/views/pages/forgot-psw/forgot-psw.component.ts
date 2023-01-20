import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-forgot-psw',
  templateUrl: './forgot-psw.component.html',
  styleUrls: ['./forgot-psw.component.scss']
})
export class ForgotPswComponent implements OnInit {

  errorMsg = ''
  isSuccess = false;

  public tempPswForm!: FormGroup;
  succMsg: any;
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

  sendTempPsw() {
    this.submitted = true;
    if (this.tempPswForm.invalid) {
      return;
    }
    //console.log(this.tempPswForm.value)
    this.userService.sendTempPsw(this.tempPswForm.value).subscribe(
      data => {
        this.isSuccess = true;
        console.log("success");
        console.log(data)
        this.succMsg = data;
      },
      err => {
        this.isSuccess = false;
        console.log("error");
        console.log(err.error)
        this.errorMsg = err.error.message;

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

  get f(): { [key: string]: AbstractControl } {
    return this.tempPswForm.controls;
  }
}

