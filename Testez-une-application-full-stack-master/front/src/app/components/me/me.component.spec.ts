import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { MeComponent } from './me.component';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from '../../interfaces/user.interface';
import { expect } from '@jest/globals';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let mockSessionService: Partial<SessionService>;
  let mockUserService: Partial<UserService>;
  let mockRouter: Router;
  let mockMatSnackBar: any;

  beforeEach(async () => {
    mockSessionService = {
      sessionInformation: {
        id: 1,
        admin: true,
        token: 'mockToken',
        type: 'user',
        username: 'testUser',
        firstName: 'Test',
        lastName: 'User'
      },
      logOut: jest.fn()
    };

    mockUserService = {
      getById: jest.fn().mockReturnValue(of({
        id: 1,
        username: 'testUser',
        firstName: 'Test',
        lastName: 'User',
        email: 'test@example.com',
        admin: true,
        password: 'hashedPassword',
        createdAt: new Date(),
        updatedAt: new Date()
      } as User)),
      delete: jest.fn().mockReturnValue(of({}))
    };

    mockMatSnackBar = {
      open: jest.fn()
    };

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        RouterTestingModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: UserService, useValue: mockUserService },
        { provide: MatSnackBar, useValue: mockMatSnackBar }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    mockRouter = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize user on ngOnInit', () => {
    component.ngOnInit();
    fixture.detectChanges();
    expect(component.user).toEqual({
      id: 1,
      username: 'testUser',
      firstName: 'Test',
      lastName: 'User',
      email: 'test@example.com',
      admin: true,
      password: 'hashedPassword',
      createdAt: expect.any(Date),
      updatedAt: expect.any(Date)
    });
    expect(mockUserService.getById).toHaveBeenCalledWith('1');
  });

  it('should navigate back when back method is called', () => {
    const spy = jest.spyOn(window.history, 'back');
    component.back();
    expect(spy).toHaveBeenCalled();
  });

  it('should delete user account and navigate to home', () => {
    const navigateSpy = jest.spyOn(mockRouter, 'navigate');
    component.delete();
    expect(mockUserService.delete).toHaveBeenCalledWith('1');
    expect(mockMatSnackBar.open).toHaveBeenCalledWith('Your account has been deleted !', 'Close', { duration: 3000 });
    expect(mockSessionService.logOut).toHaveBeenCalled();
    expect(navigateSpy).toHaveBeenCalledWith(['/']);
  });
});
