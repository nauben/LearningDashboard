import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginTextOnlyComponent } from './login-text-only.component';

describe('LoginTextOnlyComponent', () => {
  let component: LoginTextOnlyComponent;
  let fixture: ComponentFixture<LoginTextOnlyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginTextOnlyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginTextOnlyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
