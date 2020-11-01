import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from './material.module';
import { LayoutModule } from './layout/layout.module';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor} from './_helpers/jwt.interceptor';



@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    RouterModule.forRoot([]),
    BrowserModule,
    LayoutModule,
    FormsModule,
    MaterialModule,
    HttpClientModule,
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
