package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionModelTest {

    @Test
    void testValidSession() {
        // Arrange
        Session session = new Session()
                .setId(1L)
                .setName("Mathematics Class")
                .setDate(new Date())
                .setDescription("A class about calculus")
                .setTeacher(new Teacher().setId(1L).setFirstName("John").setLastName("Doe"))
                .setUsers(Arrays.asList(new User().setId(1L).setFirstName("Alice").setLastName("Doe"), new User().setId(2L).setFirstName("Bob").setLastName("Doe")));

        // Act
        Set<ConstraintViolation<Session>> violations = validate(session);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testBlankName() {
        // Arrange
        Session session = new Session()
                .setId(1L)
                .setName("")
                .setDate(new Date())
                .setDescription("A class about Yoga")
                .setTeacher(new Teacher().setId(1L).setFirstName("John").setFirstName("Doe"))
                .setUsers(Arrays.asList(new User().setId(1L).setFirstName("Alice").setLastName("Doe"), new User().setId(2L).setFirstName("Bob").setLastName("Doe")));

        // Act
        Set<ConstraintViolation<Session>> violations = validate(session);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("name", violations.iterator().next().getPropertyPath().toString());
    }

    private Set<ConstraintViolation<Session>> validate(Session session) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(session);
    }
}
