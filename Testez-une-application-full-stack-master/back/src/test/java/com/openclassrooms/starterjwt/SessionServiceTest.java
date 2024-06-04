package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSession() {
        // Given
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);

        // When
        Session createdSession = sessionService.create(session);

        // Then
        assertNotNull(createdSession);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testDeleteSession() {
        // Given
        Long sessionId = 1L;

        // When
        sessionService.delete(sessionId);

        // Then
        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @Test
    public void testFindAllSessions() {
        // Given
        List<Session> sessions = new ArrayList<>();
        when(sessionRepository.findAll()).thenReturn(sessions);

        // When
        List<Session> retrievedSessions = sessionService.findAll();

        // Then
        assertNotNull(retrievedSessions);
        assertEquals(sessions, retrievedSessions);
        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    public void testGetSessionById_Success() {
        // Given
        Long sessionId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // When
        Session retrievedSession = sessionService.getById(sessionId);

        // Then
        assertNotNull(retrievedSession);
        assertEquals(sessionId, retrievedSession.getId());
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testGetSessionById_NotFound() {
        // Given
        Long sessionId = 1L;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        // When
        Session retrievedSession = sessionService.getById(sessionId);

        // Then
        assertNull(retrievedSession);
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testUpdateSession() {
        // Given
        Long sessionId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        when(sessionRepository.save(session)).thenReturn(session);

        // When
        Session updatedSession = sessionService.update(sessionId, session);

        // Then
        assertNotNull(updatedSession);
        assertEquals(sessionId, updatedSession.getId());
        verify(sessionRepository, times(1)).save(session);
    }



    @Test
    public void testParticipate_NotFoundUser() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));

        // Then
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(userRepository, times(1)).findById(userId);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    public void testNoLongerParticipate_NotFoundSession() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        // When
        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(sessionId, userId));

        // Then
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(sessionRepository, never()).save(any());
    }
}
