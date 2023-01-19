import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  public regForm!: FormGroup;

  errorMsg=''
  isReg=true;

  constructor(
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder,
  ) { }

  ngOnInit(): void {

    this.onCreateForm();
  }

  private onCreateForm = (): void => {
    this.regForm = this.fb.group({
      userName: [null, [Validators.required]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      email: [null, [Validators.required]],
      phone: [null, [Validators.required]],
      password: [null, [Validators.required]],
      dob: [null, [Validators.required]],
      address: [null, [Validators.required]],
    });
  }

  registerUser(): void {
    console.log(this.regForm.value)
    this.userService.registerUser(this.regForm.value).subscribe(
      data => {
        this.isReg=true;
        console.log("success");
        console.log(data)
      },
      err => {
        this.isReg=false;
        console.log("error");
        console.log(err.error)
        this.errorMsg=err.error.message;

      }
    );
  }

}
