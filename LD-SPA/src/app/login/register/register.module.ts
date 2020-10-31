import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RegisterRoutingModule } from './register-routing.module';
import { RegisterComponent } from './register.component';
import {FlexLayoutModule} from '@angular/flex-layout';

@NgModule({
  imports: [
    CommonModule,
    RegisterRoutingModule,
    FlexLayoutModule
  ],
  declarations: [RegisterComponent]
})
export class RegisterModule { }
