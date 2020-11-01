import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StartRoutingModule } from './start-routing.module';
import { StartComponent } from './start.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import { KanbanModule } from '../kanban/kanban.module';

@NgModule({
  imports: [
    CommonModule,
    StartRoutingModule,
    FlexLayoutModule,
    KanbanModule,
  ],
  declarations: [StartComponent]
})
export class StartModule { }
