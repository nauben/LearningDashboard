import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';
import { ScheduleRoutingModule } from './schedule-routing.module';
import { ScheduleComponent } from './schedule.component';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
  imports: [
    CommonModule,
    ScheduleRoutingModule,
    FlexLayoutModule,
    MaterialModule,
  ],
  declarations: [ScheduleComponent]
})
export class ScheduleModule { }
