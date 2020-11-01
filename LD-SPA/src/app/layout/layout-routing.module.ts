import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HdrFtrSidebarLayoutComponent } from './hdr-ftr-sidebar-layout/hdr-ftr-sidebar-layout.component';
import { FooterCarouselLayoutComponent } from './footer-carousel-layout/footer-carousel-layout.component';

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
      { path: 'start', loadChildren: () => import('../pages/start/start.module').then(m=>m.StartModule) },
      { path: 'lernen', loadChildren: () => import('../pages/learn/learn.module').then(m=>m.LearnModule) },
      { path: 'kanban', loadChildren: () => import('../pages/kanban/kanban.module').then(m=>m.KanbanModule) },
      { path: 'vorlesungen', loadChildren: () => import('../pages/schedule/schedule.module').then(m=>m.ScheduleModule) },
      { path: 'profil', loadChildren: () => import('../pages/profile/profile.module').then(m=>m.ProfileModule) },
    ]
  },
  {
    path: '',
    component: FooterCarouselLayoutComponent,
    children: [
      { path: 'login', loadChildren: () => import('../login/login.module').then(m=>m.LoginModule) },
      { path: 'registrieren', loadChildren: () => import('../login/register/register.module').then(m=>m.RegisterModule) }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutRoutingModule { }
