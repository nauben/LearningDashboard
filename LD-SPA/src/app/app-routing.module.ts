import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HeaderSidebarComponent } from './header-sidebar/header-sidebar.component';
import { KanbanComponent } from './pages/kanban/kanban.component';
import { LearnComponent } from './pages/learn/learn.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/login/register/register.component';
import { ScheduleComponent } from './pages/schedule/schedule.component';
import { StartComponent } from './pages/start/start.component';

const routes: Routes = [
  {path: '', component: LoginComponent},
  
  {path: 'TestHeaderSidebar', component: HeaderSidebarComponent},
  {path: 'TestStart', component: StartComponent},
  {path: 'TestLernen', component: LearnComponent},
  {path: 'Registrieren', component: RegisterComponent},
  {path: 'TestKanban', component: KanbanComponent},
  {path: 'TestVorlesungen', component: ScheduleComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
