package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TeacherModelTest {

    @Test
    void testValidTeacher() {
        // Arrange
        Teacher teacher = new Teacher()
                .setId(1L)
                .setLastName("Doe")
                .setFirstName("John");

        // Act
        Set<ConstraintViolation<Teacher>> violations = validate(teacher);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testBlankLastName() {
        // Arrange
        Teacher teacher = new Teacher()
                .setId(1L)
                .setLastName("")
                .setFirstName("John");

        // Act
        Set<ConstraintViolation<Teacher>> violations = validate(teacher);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("lastName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testBlankFirstName() {
        // Arrange
        Teacher teacher = new Teacher()
                .setId(1L)
                .setLastName("Doe")
                .setFirstName("");

        // Act
        Set<ConstraintViolation<Teacher>> violations = validate(teacher);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("firstName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testMaxLastNameLength() {
        // Arrange
        String lastName = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Teacher teacher = new Teacher()
                .setId(1L)
                .setLastName(lastName)
                .setFirstName("John");

        // Act
        Set<ConstraintViolation<Teacher>> violations = validate(teacher);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("lastName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testMaxFirstNameLength() {
        // Arrange
        String firstName = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Teacher teacher = new Teacher()
                .setId(1L)
                .setLastName("Doe")
                .setFirstName(firstName);

        // Act
        Set<ConstraintViolation<Teacher>> violations = validate(teacher);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("firstName", violations.iterator().next().getPropertyPath().toString());
    }

    private Set<ConstraintViolation<Teacher>> validate(Teacher teacher) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(teacher);
    }
}
