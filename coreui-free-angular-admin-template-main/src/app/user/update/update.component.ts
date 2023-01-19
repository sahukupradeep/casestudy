import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.scss']
})
export class UpdateComponent implements OnInit {

  public updateForm!: FormGroup;

  public userName:any;

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private fb: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.userName=this.authService.getUser().userName;
    this.onCreateForm();
    this.loadUser();
  }

  private onCreateForm = (): void => {
    this.updateForm = this.fb.group({
      userName: [null, [Validators.required]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      email: [null, [Validators.required]],
      phone: [null, [Validators.required]],
      dob: [null, [Validators.required]],
      address: [null, [Validators.required]],
    });
  }
  private loadUser(){
    this.userService.getUser(this.userName).subscribe(
      data => {
        console.log("success");
        console.log(data)
       // this.updateForm.value.userName=data.userName;
       this.updateForm.controls['userName'].setValue(data.userName);
       this.updateForm.controls['firstName'].setValue(data.firstName);
       this.updateForm.controls['lastName'].setValue(data.lastName);
       this.updateForm.controls['email'].setValue(data.email);
       this.updateForm.controls['phone'].setValue(data.phone);
       this.updateForm.controls['dob'].setValue(data.dob);
       this.updateForm.controls['address'].setValue(data.address);

        
      },
      err => {
        
        console.log("error");
        console.log(err.error)
       
      }
    );   
    
  };

  updateUser():void{
    console.log(this.updateForm.value)
    this.userService.updateUser(this.updateForm.value).subscribe(
      data => {
        console.log("success");
        console.log(data)  
        this.router.navigate(['/dashboard']);      
      },
      err => {
        console.log("error");
        console.log(err.error)
       
      }
    );   
  }
}
