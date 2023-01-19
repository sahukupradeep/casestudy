import { Component,OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit {

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

    if (this.router.getCurrentNavigation()?.extras.state) {
      let userName = this.router.getCurrentNavigation()?.extras.state;
      console.log(userName+" --- ");
    }
    this.userName='';
    
  }
 
}

