import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material.module';

import { LearnRoutingModule } from './learn-routing.module';
import { LearnComponent } from './learn.component';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
  imports: [
    CommonModule,
    LearnRoutingModule,
    FlexLayoutModule,
    MaterialModule,
  ],
  declarations: [LearnComponent]
})
export class LearnModule { }
