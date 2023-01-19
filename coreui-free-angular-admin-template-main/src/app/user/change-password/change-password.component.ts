import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  public pswForm!: FormGroup;

  public userName: any;

  errorMsg = ''
  isError = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private fb: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.userName = this.authService.getUser().userName;
    this.onCreateForm();

  }

  private onCreateForm = (): void => {
    this.pswForm = this.fb.group({
      password: [null, [Validators.required]],
      newPassword: [null, [Validators.required]],
      rePassword: [null, [Validators.required]],
    });
  }
  changePassword(): void {
    let value = this.pswForm.value;
    value.userName = this.userName;
    if (value.newPassword != value.rePassword) {
      this.isError = true;
      this.errorMsg = "Mismatch newpassword and re-password";
      return;
    }
    if (value.newPassword == value.password) {
      this.isError = true;
      this.errorMsg = "both newpassword and oldpassword are same";
      return;
    }
    this.userService.changePassword(value).subscribe(
      data => {
        console.log("success");
        console.log(data)
      },
      err => {
        console.log("error");
        console.log(err.error)
        this.isError = true;
        this.errorMsg = err.error.message;

      }
    );
  }
}