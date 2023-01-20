import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  public regForm!: FormGroup;

  submitted = false;

  profile: any={
    userName:null,
    firstName:null,
    lastName:null,
    email:null,
    phone:null,
    dob:null,
    address:null
  };

  errorMsg=''
  isReg=false;
  successMsg='';

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
  get f(): { [key: string]: AbstractControl } {
    return this.regForm.controls;
  }

  registerUser(): void {
    this.submitted = true;
    if (this.regForm.invalid) {
      return;
    }
    //console.log(this.regForm.value)
    this.userService.registerUser(this.regForm.value).subscribe(
      data => {
        this.isReg=true;
        console.log("success");
        console.log(data)
        //this.router.navigate(['/dashboard']);
        this.successMsg=this.regForm.value.userName +" User register successfull!"
        this.loadUser(this.regForm.value.userName);
      },
      err => {
        this.isReg=false;
        console.log("error");
        console.log(err.error)
        this.errorMsg=err.error.message;

      }
    );
  }
  private loadUser(userName: any) {
    this.userService.loadProfile(userName).subscribe(
      data => {
        console.log("success");
        console.log(data)
        this.profile=data
      },
      err => {
        console.log("error");
        console.log(err.error)

      }
    );
  }

}
