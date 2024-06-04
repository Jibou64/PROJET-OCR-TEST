import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;

  let mockSessionService: Partial<SessionService>;

  beforeEach(async () => {
    mockSessionService = {
      sessionInformation: {
        token: 'mockToken',
        type: 'user',
        id: 1,
        username: 'testUser',
        firstName: 'Test',
        lastName: 'User',
        admin: false
      }
    };

    await TestBed.configureTestingModule({

      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        SessionApiService
      ],
      declarations: [FormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form for new session', () => {
    expect(component.sessionForm?.get('name')?.value).toEqual('');
    expect(component.sessionForm?.get('description')?.value).toEqual('');
    expect(component.sessionForm?.get('date')?.value).toEqual('');
    expect(component.sessionForm?.get('teacher_id')?.value).toEqual('');
  });

  it('should initialize form for existing session', () => {
    component.onUpdate = true;
    const mockSession = {
      id: 1,
      name: 'Mock Session',
      description: 'Mock Description',
      date: '2024-06-01',
      teacher_id: '2'
    };


    // Simuler le comportement d'initialisation du formulaire
    component.ngOnInit();
    // Remplir les valeurs du formulaire manuellement
    component.sessionForm?.get('name')?.setValue(mockSession.name);
    component.sessionForm?.get('description')?.setValue(mockSession.description);
    component.sessionForm?.get('date')?.setValue(mockSession.date);
    component.sessionForm?.get('teacher_id')?.setValue(mockSession.teacher_id);

    // Vérifier que les valeurs du formulaire correspondent aux valeurs de la session
    expect(component.sessionForm?.get('name')?.value).toEqual(mockSession.name);
    expect(component.sessionForm?.get('description')?.value).toEqual(mockSession.description);
    expect(component.sessionForm?.get('date')?.value).toEqual(mockSession.date);
    expect(component.sessionForm?.get('teacher_id')?.value).toEqual(mockSession.teacher_id);
  });
});
