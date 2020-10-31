import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HdrFtrSidebarLayoutComponent } from './hdr-ftr-sidebar-layout.component';

describe('HdrFtrSidebarLayoutComponent', () => {
  let component: HdrFtrSidebarLayoutComponent;
  let fixture: ComponentFixture<HdrFtrSidebarLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HdrFtrSidebarLayoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HdrFtrSidebarLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
