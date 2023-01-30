import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NavigationExtras, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { saveAs} from 'file-saver';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  public searchForm!: FormGroup;

  public enableDesableForm!:FormGroup;

  public userName:any;

  profile: any={
    userName:null,
    firstName:null,
    lastName:null,
    email:null,
    phone:null,
    dob:null,
    address:null,
    isAdmin:null,
    isDisable:null
  };

  isError=false;
  errorMsg:any;
  listUser:any;
  isSuccess=false;
  isProfile=false;
  isUpdate=false;
  updateMsg:any;

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
    this.enableDesable();

  }

  private onCreateForm = (): void => {
    this.searchForm = this.fb.group({
      userName: [null, [Validators.required]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      dob: [null, [Validators.required]],
      searchDate: [null, [Validators.required]],
    });
  }

  private enableDesable = (): void => {
    this.enableDesableForm = this.fb.group({
      userName: [null, [Validators.required]],
      isAdmin: [null, [Validators.required]],
      isDisable: [null, [Validators.required]],
    });
  }

  search():void{
    let value=this.searchForm.value;
    console.log(value);
    if(value.userName==null && value.firstName==null && value.lastName==null && value.dob==null && value.searchDate==null){
      this.isError=true;
      this.errorMsg="required any one field";
      return;
    }
    this.userService.search(value).subscribe(
      data=> {
        console.log("success");
        // console.log(data)
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

  selectedFiles?: FileList;
  currentFile?: File;

  downloadAudit(userName:any){
    console.log(userName);

    this.userService.downloadAudit(userName)
      .subscribe(blob => fileSaver.saveAs(blob, userName+'.xlsx'));
  }


  loadProfile(userName:any) {

    this.userService.loadProfile(userName).subscribe(
      data => {
        console.log("success");
        this.isProfile=true;
        this.profile=data
        this.isUpdate=false;
        this.updateMsg="";
        this.enableDesableForm.controls['userName'].setValue(this.profile.userName);

        if( this.profile.roleId!=null &&  this.profile.roleId == 1){
          this.enableDesableForm.controls['isAdmin'].setValue(true);
        }else{
          this.enableDesableForm.controls['isAdmin'].setValue(false);
        }

        if(this.profile.status !=null && this.profile.status == 1){
          this.enableDesableForm.controls['isDisable'].setValue(false);
        }else{
          this.enableDesableForm.controls['isDisable'].setValue(true);
        }
        
        
      },
      err => {
        console.log("error");
        console.log(err.error)

      }
    );
  }

  searchList(){
    this.isProfile=false;
  }
  
  updateRole(){
    console.log(this.enableDesableForm.value);
    this.userService.updateRole(this.enableDesableForm.value).subscribe(
      data=> {
        console.log("success");
        this.isSuccess=true;
        this.isUpdate=true;

        this.updateMsg="Update Successfull!";
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


