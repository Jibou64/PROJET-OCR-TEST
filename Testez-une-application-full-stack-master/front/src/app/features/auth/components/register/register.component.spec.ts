import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import {of, throwError} from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { RegisterComponent } from './register.component';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: AuthService;
  let router: Router;

  beforeEach(async () => {
    // Stub AuthService and Router
    const authServiceStub = {
      register: jest.fn()
    };
    const routerSpy = { navigate: jest.fn() };

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [
        FormBuilder,
        { provide: AuthService, useValue: authServiceStub },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    // Initialize authService and router
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call authService.register when submitting form', () => {
    // Arrange
    const registerSpy = jest.spyOn(authService, 'register').mockReturnValue(of());

    // Act
    component.form.setValue({
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'password123'
    });
    component.submit();

    // Assert
    expect(registerSpy).toHaveBeenCalled();
  });


  it('should navigate to /login after successful registration', () => {
    // Arrange
    jest.spyOn(authService, 'register').mockReturnValue(of(void 0)); // Simulate successful registration

    // Act
    component.submit();

    // Assert
    expect(router.navigate).toHaveBeenCalledWith(['/login']); // Should navigate to /login after successful registration
  });

  it('should display error field if registration fails', () => {
    // Arrange
    const errorMessage = 'Registration failed';
    jest.spyOn(authService, 'register').mockReturnValue(throwError(errorMessage)); // Simulate registration failure

    // Act
    component.submit();

    // Assert
    expect(component.onError).toBe(true); // Error flag should be set to true
  });
});
