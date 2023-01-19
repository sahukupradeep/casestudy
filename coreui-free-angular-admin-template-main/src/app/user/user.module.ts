import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { DetailsComponent } from './details/details.component';
import { SearchComponent } from './search/search.component';
import { AddComponent } from './add/add.component';
import { UpdateComponent } from './update/update.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ButtonModule, CardModule, FormModule, GridModule } from '@coreui/angular';
import { IconModule } from '@coreui/icons-angular';
import { ListComponent } from './list/list.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    DetailsComponent,
    SearchComponent,
    AddComponent,
    UpdateComponent,
    ChangePasswordComponent,
    ListComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    CommonModule,
    CardModule,
    ButtonModule,
    GridModule,
    IconModule,
    FormModule,
    ReactiveFormsModule
  ]
})
export class UserModule { }
