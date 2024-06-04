package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllTeachers() {
        // Given
        List<Teacher> teachers = new ArrayList<>();
        when(teacherRepository.findAll()).thenReturn(teachers);

        // When
        List<Teacher> retrievedTeachers = teacherService.findAll();

        // Then
        assertNotNull(retrievedTeachers);
        assertEquals(teachers, retrievedTeachers);
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void testFindTeacherById_Success() {
        // Given
        Long teacherId = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        // When
        Teacher retrievedTeacher = teacherService.findById(teacherId);

        // Then
        assertNotNull(retrievedTeacher);
        assertEquals(teacherId, retrievedTeacher.getId());
        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    public void testFindTeacherById_NotFound() {
        // Given
        Long teacherId = 1L;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        // When
        Teacher retrievedTeacher = teacherService.findById(teacherId);

        // Then
        assertNull(retrievedTeacher);
        verify(teacherRepository, times(1)).findById(teacherId);
    }
}
