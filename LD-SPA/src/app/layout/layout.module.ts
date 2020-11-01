import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CarouselComponent } from './carousel/carousel.component';
import { FooterComponent } from './footer/footer.component';
import { FooterCarouselLayoutComponent } from './footer-carousel-layout/footer-carousel-layout.component';
import { HdrFtrSidebarLayoutComponent } from './hdr-ftr-sidebar-layout/hdr-ftr-sidebar-layout.component';
import { HeaderComponent } from './header/header.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import { LayoutRoutingModule } from './layout-routing.module';
import { SidebarComponent } from './sidebar/sidebar.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';


@NgModule({
  declarations: [
    HdrFtrSidebarLayoutComponent,
    FooterCarouselLayoutComponent,
    FooterComponent,
    HeaderComponent,
    
    CarouselComponent,
    FooterComponent,
    FooterCarouselLayoutComponent,
    HdrFtrSidebarLayoutComponent,
    SidebarComponent,
    HeaderComponent,
    PageNotFoundComponent,
  ],
  imports: [
    CommonModule,
    LayoutRoutingModule,
    FlexLayoutModule,
    BrowserAnimationsModule,
  ],
})
export class LayoutModule { }
