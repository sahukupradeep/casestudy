import { Component, OnInit } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

import { DashboardChartsData, IChartProps } from './dashboard-charts-data';

@Component({
  templateUrl: 'dashboard.component.html',
  styleUrls: ['dashboard.component.scss']
})
export class DashboardComponent implements OnInit {


  profile: any={
    userName:null,
    firstName:null,
    lastName:null,
    email:null,
    phone:null,
    dob:null,
    address:null
  };
  userName: any;
  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
  ) { }


  ngOnInit(): void {
    this.userName = this.authService.getUser().userName;
    this.loadProfile();
  }

  private loadProfile() {
    this.userService.loadProfile(this.userName).subscribe(
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


