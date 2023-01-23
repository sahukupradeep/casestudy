import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

import { navItems } from './_nav';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html',
})
export class DefaultLayoutComponent implements OnInit {




  role:any=null;

  constructor(private authService: AuthService) { }
  
  ngOnInit(): void {
    this.role=this.authService.role;
    console.log(this.role)
  }


  public navItems = navItems;

  public perfectScrollbarConfig = {
    suppressScrollX: true,
  };

  
}
