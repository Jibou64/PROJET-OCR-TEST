package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionMapper sessionMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById_Success() {
        Session session = new Session();
        session.setId(1L);
        when(sessionService.getById(anyLong())).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(new SessionDto());

        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(sessionService, times(1)).getById(1L);
        verify(sessionMapper, times(1)).toDto(session);
    }

    @Test
    public void testFindById_NotFound() {
        when(sessionService.getById(anyLong())).thenReturn(null);

        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(sessionService, times(1)).getById(1L);
    }

    @Test
    public void testFindById_BadRequest() {
        ResponseEntity<?> response = sessionController.findById("invalid");

        assertEquals(400, response.getStatusCodeValue());
        verify(sessionService, never()).getById(anyLong());
    }

    @Test
    public void testFindAll_Success() {
        List<Session> sessions = new ArrayList<>();
        when(sessionService.findAll()).thenReturn(sessions);
        when(sessionMapper.toDto(anyList())).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = sessionController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(sessionService, times(1)).findAll();
        verify(sessionMapper, times(1)).toDto(sessions);
    }

    @Test
    public void testCreate_Success() {
        SessionDto sessionDto = new SessionDto();
        Session session = new Session();
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionService.create(any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.create(sessionDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(sessionService, times(1)).create(session);
        verify(sessionMapper, times(1)).toEntity(sessionDto);
        verify(sessionMapper, times(1)).toDto(session);
    }

    @Test
    public void testUpdate_Success() {
        SessionDto sessionDto = new SessionDto();
        Session session = new Session();
        session.setId(1L);
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionService.update(anyLong(), any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.update("1", sessionDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(sessionService, times(1)).update(1L, session);
        verify(sessionMapper, times(1)).toEntity(sessionDto);
        verify(sessionMapper, times(1)).toDto(session);
    }

    @Test
    public void testUpdate_BadRequest() {
        SessionDto sessionDto = new SessionDto();

        ResponseEntity<?> response = sessionController.update("invalid", sessionDto);

        assertEquals(400, response.getStatusCodeValue());
        verify(sessionService, never()).update(anyLong(), any(Session.class));
    }


    @Test
    public void testParticipate_Success() {
        doNothing().when(sessionService).participate(anyLong(), anyLong());

        ResponseEntity<?> response = sessionController.participate("1", "1");

        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).participate(1L, 1L);
    }

    @Test
    public void testParticipate_BadRequest() {
        ResponseEntity<?> response = sessionController.participate("invalid", "invalid");

        assertEquals(400, response.getStatusCodeValue());
        verify(sessionService, never()).participate(anyLong(), anyLong());
    }

    @Test
    public void testNoLongerParticipate_Success() {
        doNothing().when(sessionService).noLongerParticipate(anyLong(), anyLong());

        ResponseEntity<?> response = sessionController.noLongerParticipate("1", "1");

        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).noLongerParticipate(1L, 1L);
    }

    @Test
    public void testNoLongerParticipate_BadRequest() {
        ResponseEntity<?> response = sessionController.noLongerParticipate("invalid", "invalid");

        assertEquals(400, response.getStatusCodeValue());
        verify(sessionService, never()).noLongerParticipate(anyLong(), anyLong());
    }

    @Test
    public void testDeleteSession() {
        Long sessionId = 1L;
        doNothing().when(sessionService).delete(sessionId);

        sessionService.delete(sessionId);

        verify(sessionService, times(1)).delete(sessionId);
    }

    @Test
    void testSave_ValidId() {
        // Arrange
        Long validId = 1L;
        Session session = new Session();
        session.setId(validId);
        when(sessionService.getById(validId)).thenReturn(session);

        // Act
        ResponseEntity<?> response = sessionController.save(validId.toString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService, times(1)).delete(validId);
    }

    @Test
    void testSave_InvalidId() {
        // Arrange
        String invalidId = "invalid";
        when(sessionService.getById(any())).thenReturn(null);

        // Act
        ResponseEntity<?> response = sessionController.save(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).delete(any());
    }

    @Test
    void testSave_InvalidNumberFormatException() {
        // Arrange
        String invalidId = "invalid";
        when(sessionService.getById(any())).thenThrow(NumberFormatException.class);

        // Act
        ResponseEntity<?> response = sessionController.save(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).delete(any());
    }

}
