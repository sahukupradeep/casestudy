import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../services/auth.guard';
import { AddComponent } from './add/add.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { DetailsComponent } from './details/details.component';
import { ListComponent } from './list/list.component';
import { SearchComponent } from './search/search.component';
import { UpdateComponent } from './update/update.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'search',
        component: SearchComponent,
        data: {
          title: 'Search'
        },
        canActivate: [AuthGuard]
      },
      {
        path: 'add',
        component: AddComponent,
        data: {
          title: 'Add'
        },
        canActivate: [AuthGuard]
      },
      {
        path: 'change-password',
        component: ChangePasswordComponent,
        data: {
          title: 'Change Password'
        },
        canActivate: [AuthGuard]
      },
      {
        path: 'details',
        component: DetailsComponent,
        data: {
          title: 'Details'
        },
        canActivate: [AuthGuard]
      },
      {
        path: 'update',
        component: UpdateComponent,
        data: {
          title: 'Update'
        },
        canActivate: [AuthGuard]
      },
      {
        path: 'list',
        component: ListComponent,
        data: {
          title: 'List'
        },
        canActivate: [AuthGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
