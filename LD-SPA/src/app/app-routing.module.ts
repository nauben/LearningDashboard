import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HeaderSidebarComponent } from './header-sidebar/header-sidebar.component';
import { LearnComponent } from './pages/learn/learn.component';
import { LoginComponent } from './pages/login/login.component';
import { StartComponent } from './pages/start/start.component';

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'TestHeaderSidebar', component: HeaderSidebarComponent},
  {path: 'TestStart', component: StartComponent},
  {path: 'TestLernen', component: LearnComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
