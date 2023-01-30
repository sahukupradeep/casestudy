import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-download',
  templateUrl: './download.component.html',
  styleUrls: ['./download.component.scss']
})
export class DownloadComponent {

  public searchForm!: FormGroup;
  
  isError=false;
  errorMsg:any;

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private fb: FormBuilder,
  ) { }


  ngOnInit(): void {

    this.onCreateForm();

  }

  private onCreateForm = (): void => {
    this.searchForm = this.fb.group({
      userName: [null, [Validators.required]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      auditDate:[null, [Validators.required]],
      toDate:[null, [Validators.required]],
    });
  }



  search():void{
    let value=this.searchForm.value;
    console.log(value);
    if(value.userName==null && value.firstName==null && value.lastName==null && value.auditDate==null){
      this.isError=true;
      this.errorMsg="required any one field";
      return;
    }
    if(value.auditDate!=null && value.toDate==null){
      this.isError=true;
      this.errorMsg="required audit to date";
      return;
    }

    if(value.auditDate!=null && value.toDate!=null &&(value.auditDate>value.toDate)){
      this.isError=true;
      this.errorMsg="invalid audit to date";
      return;
    }
    this.isError=false;
      this.errorMsg=null;
    this.userService.searchDownload(value).subscribe(blob => fileSaver.saveAs(blob, 'Audit.xlsx'));
  }



}
