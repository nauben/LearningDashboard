import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HdrFtrSidebarLayoutComponent } from './hdr-ftr-sidebar-layout/hdr-ftr-sidebar-layout.component';
import { FooterCarouselLayoutComponent } from './footer-carousel-layout/footer-carousel-layout.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AuthGuard } from '../_helpers/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/start',
    pathMatch: 'full'
  },
  {
    path: '',
    component: HdrFtrSidebarLayoutComponent,
    children: [
      { path: 'start', loadChildren: () => import('../pages/start/start.module').then(m=>m.StartModule), canActivate: [AuthGuard] },
      { path: 'lernen', loadChildren: () => import('../pages/learn/learn.module').then(m=>m.LearnModule), canActivate: [AuthGuard] },
      { path: 'kanban', loadChildren: () => import('../pages/kanban/kanban.module').then(m=>m.KanbanModule), canActivate: [AuthGuard] },
      { path: 'vorlesungen', loadChildren: () => import('../pages/schedule/schedule.module').then(m=>m.ScheduleModule), canActivate: [AuthGuard] },
      { path: 'profil', loadChildren: () => import('../pages/profile/profile.module').then(m=>m.ProfileModule), canActivate: [AuthGuard] },
    ]
  },
  {
    path: '',
    component: FooterCarouselLayoutComponent,
    children: [
      { path: 'login', loadChildren: () => import('../login/login.module').then(m=>m.LoginModule) },
      { path: 'registrieren', loadChildren: () => import('../login/register/register.module').then(m=>m.RegisterModule) },
      { path: '**', component: PageNotFoundComponent },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutRoutingModule { }
