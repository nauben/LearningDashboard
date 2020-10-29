import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './pages/kanban/main/main.component';
import { HeaderSidebarComponent } from './header-sidebar/header-sidebar.component';
import { LoginComponent } from './pages/login/login.component';
import { StartComponent } from './pages/start/start.component';
import { LearnComponent } from './pages/learn/learn.component';


@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    HeaderSidebarComponent,
    LoginComponent,
    StartComponent,
    LearnComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
