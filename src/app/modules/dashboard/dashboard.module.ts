import { NgModule } from '@angular/core';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { FormsModule } from '@angular/forms';


@NgModule({
  imports: [DashboardRoutingModule,
    FormsModule
  ],
})
export class DashboardModule {}
