import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../services/auth.guard';
import { DownloadComponent } from './download/download.component';

const routes: Routes = [
  // {
  //   path: '',
  //   children: [
  //     {
  //       path: 'report',
  //       component: DownloadComponent,
  //       data: {
  //         title: 'Report'
  //       },
  //       canActivate: [AuthGuard]
  //     },
  //   ]
  // }
  {
    path: '',
    component: DownloadComponent,
    data: {
      title: $localize`Report`
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportRoutingModule { }
