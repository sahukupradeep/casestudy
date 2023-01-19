import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
        }
      },
      {
        path: 'add',
        component: AddComponent,
        data: {
          title: 'Add'
        }
      },
      {
        path: 'change-password',
        component: ChangePasswordComponent,
        data: {
          title: 'Change Password'
        }
      },
      {
        path: 'details',
        component: DetailsComponent,
        data: {
          title: 'Details'
        }
      },
      {
        path: 'update',
        component: UpdateComponent,
        data: {
          title: 'Update'
        }
      },
      {
        path: 'list',
        component: ListComponent,
        data: {
          title: 'List'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
