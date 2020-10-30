import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from './material.module';

import { AppRoutingModule } from './app-routing.module';
import { LoginRoutingModule } from './login/login-routing.module';
import { PagesRoutingModule } from './pages/pages-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './pages/kanban/main/main.component';
import { HeaderSidebarComponent } from './header-sidebar/header-sidebar.component';
import { LoginComponent } from './login/login.component';
import { StartComponent } from './pages/start/start.component';
import { LearnComponent } from './pages/learn/learn.component';
import { RegisterComponent } from './login/register/register.component';
import { KanbanComponent } from './pages/kanban/kanban.component';
import { ScheduleComponent } from './pages/schedule/schedule.component';
import { LoginTextOnlyComponent } from './login/login-text-only/login-text-only.component';


@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    HeaderSidebarComponent,
    LoginComponent,
    StartComponent,
    LearnComponent,
    RegisterComponent,
    KanbanComponent,
    ScheduleComponent,
    LoginTextOnlyComponent,
  ],
  imports: [
    BrowserModule,
    LoginRoutingModule,
    PagesRoutingModule,
    AppRoutingModule,
    FormsModule,
    MaterialModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
