import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { KanbanComponent } from './kanban/kanban.component';
import { ScheduleComponent } from './schedule/schedule.component';
import { LearnComponent } from './learn/learn.component';
import { StartComponent } from './start/start.component';


const pagesRoutes: Routes = [
  {path: 'kanban', component: KanbanComponent},
  {path: 'vorlesungen', component: ScheduleComponent},
  {path: 'lernen', component: LearnComponent},
  {path: 'start', component: StartComponent},

];

@NgModule({
  imports: [RouterModule.forChild(pagesRoutes)],
  exports: [RouterModule]
})
export class PagesRoutingModule { }
