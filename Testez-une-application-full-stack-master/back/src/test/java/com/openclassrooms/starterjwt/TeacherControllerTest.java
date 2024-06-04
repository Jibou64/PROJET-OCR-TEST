package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void testFindById_WithValidId() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        ResponseEntity<?> response = teacherController.findById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(teacherDto, response.getBody());
    }

    @Test
    public void testFindById_WithInvalidId() {
        when(teacherService.findById(any())).thenReturn(null);

        ResponseEntity<?> response = teacherController.findById("invalid");

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testFindById_WithNumberFormatException() {
        ResponseEntity<?> response = teacherController.findById("abc");

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testFindAll() {
        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teachers.add(teacher1);
        teachers.add(teacher2);

        List<TeacherDto> teacherDtos = new ArrayList<>();
        TeacherDto teacherDto1 = new TeacherDto(); // Créez un objet TeacherDto pour simuler le mappage
        teacherDto1.setId(1L);
        TeacherDto teacherDto2 = new TeacherDto(); // Créez un objet TeacherDto pour simuler le mappage
        teacherDto2.setId(2L);
        teacherDtos.add(teacherDto1);
        teacherDtos.add(teacherDto2);

        when(teacherService.findAll()).thenReturn(teachers);
        when(teacherMapper.toDto(teachers)).thenReturn(teacherDtos);

        ResponseEntity<?> response = teacherController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(teacherDtos, response.getBody());
    }

    @Test
    public void testFindAll_WithNoTeachers() {
        when(teacherService.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = teacherController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((List<?>) response.getBody()).isEmpty());
    }

}
