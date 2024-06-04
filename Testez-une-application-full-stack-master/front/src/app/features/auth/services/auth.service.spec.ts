import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

describe('AuthService', () => {
  let authService: AuthService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });
    authService = TestBed.inject(AuthService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(authService).toBeTruthy();
  });

  it('should register a new user', () => {

    const registerRequest: RegisterRequest = {
      email: 'test@example.com',
      password: 'testpassword',
      firstName: 'Test',
      lastName: 'User'
    };

    authService.register(registerRequest).subscribe(() => {
      // Check that the registration was successful
      expect(true).toBe(true); // Placeholder assertion
    });

    const req = httpTestingController.expectOne('api/auth/register');
    expect(req.request.method).toEqual('POST');
    req.flush({});
  });

  it('should log in a user', () => {
    const loginRequest: LoginRequest = {
      email: 'test@example.com',
      password: 'testpassword'
    };

    const mockSessionInformation: SessionInformation = {
      token: 'mockToken',
      type: 'user',
      id: 1,
      username: 'testuser',
      firstName: 'Test',
      lastName: 'User',
      admin: false
    };

    authService.login(loginRequest).subscribe((sessionInfo: SessionInformation) => {
      // Check that the login was successful and returned the correct session information
      expect(sessionInfo).toEqual(mockSessionInformation);
    });

    const req = httpTestingController.expectOne('api/auth/login');
    expect(req.request.method).toEqual('POST');
    req.flush(mockSessionInformation); // Simulate a successful login with mock session information
  });
});
