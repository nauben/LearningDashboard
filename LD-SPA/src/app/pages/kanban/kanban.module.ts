import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KanbanRoutingModule } from './kanban-routing.module';
import { KanbanComponent } from './kanban.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import { KanbanboardComponent } from './kanbanboard/kanbanboard.component';
import { TaskeditComponent } from './taskedit/taskedit.component';

@NgModule({
  imports: [
    CommonModule,
    KanbanRoutingModule,
    FlexLayoutModule
  ],
  exports:[
    KanbanboardComponent,
  ],
  
  declarations: [KanbanComponent, KanbanboardComponent, TaskeditComponent],

})
export class KanbanModule { }
