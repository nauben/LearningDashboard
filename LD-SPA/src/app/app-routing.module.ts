import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HeaderSidebarComponent } from './header-sidebar/header-sidebar.component';
import { KanbanComponent } from './pages/kanban/kanban.component';
import { LearnComponent } from './pages/learn/learn.component';
import { LoginTextOnlyComponent } from './pages/login/login-text-only/login-text-only.component';
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
  {path: 'LoginTO', component: LoginTextOnlyComponent},
  {path: 'TestRegister', component: RegisterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
