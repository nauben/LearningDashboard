import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login.component';
import { RegisterComponent } from './register/register.component';
import { LoginTextOnlyComponent } from './login-text-only/login-text-only.component';


const loginRoutes: Routes = [
  
  {path: 'registrieren', component: RegisterComponent},
  {path: 'login', component: LoginTextOnlyComponent, pathMatch: 'full'},
  {path: '', component: LoginTextOnlyComponent, pathMatch: 'full'},

  {path: 'login',
  component: LoginComponent,
  children: [
      {
      path: 'login',
      component: LoginTextOnlyComponent,
      },
    ],
  },
  
  /* {path: 'login',
  component: LoginComponent,
  children: [
      {
      path: 'registrieren',
      component: RegisterComponent,
      },
    ],
  }, */

];

@NgModule({
  imports: [RouterModule.forChild(loginRoutes)],
  exports: [RouterModule]
})
export class LoginRoutingModule { }
