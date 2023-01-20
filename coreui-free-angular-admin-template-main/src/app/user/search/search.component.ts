import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NavigationExtras, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  public searchForm!: FormGroup;

  public userName:any;

  isError=false;
  errorMsg:any;
  listUser:any;
  isSuccess=false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private fb: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.userName=this.authService.getUser().userName;
    this.isSuccess=false;
    this.isError=false;
    this.onCreateForm();

  }

  private onCreateForm = (): void => {
    this.searchForm = this.fb.group({
      userName: [null, [Validators.required]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      dob: [null, [Validators.required]],
    });
  }

  search():void{
    let value=this.searchForm.value;
    console.log(value);
    if(value.userName==null && value.firstName==null && value.lastName==null && value.dob==null){
      this.isError=true;
      this.errorMsg="required any one field";
      return;
    }
    this.userService.search(value).subscribe(
      data=> {
        console.log("success");
        console.log(data)
        this.listUser=data;
        if(this.listUser==null){
          //this.isSuccess=true;
          //this.isError = true;
          this.errorMsg = "no result found";
        }
        this.isSuccess=true;
      },
      err => {
        console.log("error");
        console.log(err.error)
        this.isError = true;
        this.errorMsg = err.error.message;

      }
    );

    

  }

  userDetails(userName:any){
    console.log(userName);

    let navigationExtras: NavigationExtras = {
      state: {
        userName: userName,
      },
    };
    this.router.navigate(["/user/details"], navigationExtras);
  }
}
