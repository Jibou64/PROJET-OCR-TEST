package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    private Set<ConstraintViolation<User>> validate(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(user);
    }

    @Test
    void testValidUser() {
        // Arrange
        User user = new User()
                .setId(1L)
                .setEmail("john.doe@example.com")
                .setLastName("Doe")
                .setFirstName("John")
                .setPassword("password123")
                .setAdmin(true);

        // Act
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertTrue(violations.isEmpty());
    }


    @Test
    void testInvalidEmail() {
        // Arrange
        User user = new User()
                .setId(1L)
                .setEmail("not-an-email")
                .setLastName("Doe")
                .setFirstName("John")
                .setPassword("password123")
                .setAdmin(true);

        // Act
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("email", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testMaxLastNameLength() {
        // Arrange
        String lastName = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        User user = new User()
                .setId(1L)
                .setEmail("john.doe@example.com")
                .setLastName(lastName)
                .setFirstName("John")
                .setPassword("password123")
                .setAdmin(true);

        // Act
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("lastName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testMaxFirstNameLength() {
        // Arrange
        String firstName = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        User user = new User()
                .setId(1L)
                .setEmail("john.doe@example.com")
                .setLastName("Doe")
                .setFirstName(firstName)
                .setPassword("password123")
                .setAdmin(true);

        // Act
        Set<ConstraintViolation<User>> violations = validate(user);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("firstName", violations.iterator().next().getPropertyPath().toString());
    }


    @Test
    void testCreatedAt() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        User user = new User()
                .setId(1L)
                .setEmail("john.doe@example.com")
                .setLastName("Doe")
                .setFirstName("John")
                .setPassword("password123")
                .setAdmin(true)
                .setCreatedAt(now);

        // Act
        LocalDateTime createdAt = user.getCreatedAt();

        // Assert
        assertEquals(now, createdAt);
    }

    @Test
    void testUpdatedAt() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        User user = new User()
                .setId(1L)
                .setEmail("john.doe@example.com")
                .setLastName("Doe")
                .setFirstName("John")
                .setPassword("password123")
                .setAdmin(true)
                .setUpdatedAt(now);

        // Act
        LocalDateTime updatedAt = user.getUpdatedAt();

        // Assert
        assertEquals(now, updatedAt);
    }
}
