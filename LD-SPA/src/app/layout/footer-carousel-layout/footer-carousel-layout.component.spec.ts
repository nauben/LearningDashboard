import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterCarouselLayoutComponent } from './footer-carousel-layout.component';

describe('FooterCarouselLayoutComponent', () => {
  let component: FooterCarouselLayoutComponent;
  let fixture: ComponentFixture<FooterCarouselLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FooterCarouselLayoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FooterCarouselLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
