import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MainRoutingModule } from './main-routing.module';
import { TestComponent } from './test/test.component';
import { BadgeModule, CardModule, GridModule } from '@coreui/angular';
import { ChartjsModule } from '@coreui/angular-chartjs';
import { RegisterComponent } from '../views/pages/register/register.component';
import { PagesModule } from '../views/pages/pages.module';


@NgModule({
  declarations: [
    TestComponent

  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    CommonModule,
    ChartjsModule,
    CardModule,
    GridModule,
    BadgeModule,
    PagesModule
  ]
})
export class MainModule { }
