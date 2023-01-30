import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportRoutingModule } from './report-routing.module';
import { DownloadComponent } from './download/download.component';

import { ButtonModule, CardModule, FormModule, GridModule } from '@coreui/angular';
import { IconModule } from '@coreui/icons-angular';

import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    DownloadComponent
  ],
  imports: [
    CommonModule,
    ReportRoutingModule,
    CardModule,
    ButtonModule,
    GridModule,
    IconModule,
    FormModule,
    ReactiveFormsModule
  ]
})
export class ReportModule { }
