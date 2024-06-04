import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from '../../services/auth.service';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let sessionService: SessionService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ],
      providers: [AuthService, SessionService]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should redirect to /sessions on successful login', () => {
    const response: SessionInformation = {
      token: 'abc123',
      type: 'example',
      id: 1,
      username: 'testuser',
      firstName: 'John',
      lastName: 'Doe',
      admin: true
    };

    const navigateSpy = jest.spyOn(router, 'navigate').mockResolvedValueOnce(true);
    jest.spyOn(authService, 'login').mockReturnValueOnce(of(response));

    component.form.setValue({
      email: 'test@example.com',
      password: 'password'
    });
    component.submit();

    expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
    expect(sessionService.sessionInformation).toEqual(response);
  });

  it('should set onError to true on login error', () => {
    jest.spyOn(authService, 'login').mockReturnValueOnce(
      throwError({ error: 'Login failed' })
    );

    component.form.setValue({
      email: 'test@example.com',
      password: 'password'
    });
    component.submit();

    expect(component.onError).toBeTruthy();
  });
});
