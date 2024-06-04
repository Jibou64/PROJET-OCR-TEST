package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class SessionMapperTest {

    @InjectMocks
    private SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity() {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Test Session");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Collections.singletonList(1L));

        Teacher teacher = new Teacher().setId(1L).setLastName("Smith").setFirstName("John");
        User user = new User().setId(1L).setFirstName("userFirst1").setLastName("userLast1");

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user);

        // Act
        Session session = sessionMapper.toEntity(sessionDto);

        // Assert
        assertNotNull(session);
        assertEquals("Test Session", session.getDescription());
        assertNotNull(session.getTeacher());
        assertEquals(1L, session.getTeacher().getId());
        assertNotNull(session.getUsers());
        assertEquals(1, session.getUsers().size());
        assertEquals(1L, session.getUsers().get(0).getId());
    }

    @Test
    void testToDto() {
        // Arrange
        Teacher teacher = new Teacher().setId(1L).setLastName("Smith").setFirstName("John");
        User user = new User().setId(1L).setFirstName("userFirst1").setLastName("userLast1");

        Session session = new Session();
        session.setDescription("Test Session");
        session.setTeacher(teacher);
        session.setUsers(Collections.singletonList(user));

        // Act
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Assert
        assertNotNull(sessionDto);
        assertEquals("Test Session", sessionDto.getDescription());
        assertEquals(1L, sessionDto.getTeacher_id());
        assertNotNull(sessionDto.getUsers());
        assertEquals(1, sessionDto.getUsers().size());
        assertEquals(1L, sessionDto.getUsers().get(0));
    }
}
