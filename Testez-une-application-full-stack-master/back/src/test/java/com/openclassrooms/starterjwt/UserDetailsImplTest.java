package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    @Test
    void testUserDetailsImplConstructorAndGetters() {

        // Arrange
        Long id = 1L;
        String username = "john_doe";
        String firstName = "John";
        String lastName = "Doe";
        Boolean admin = true;
        String password = "password123";

        // Act
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .admin(admin)
                .password(password)
                .build();

        // Assert
        assertNotNull(userDetails);
        assertEquals(id, userDetails.getId());
        assertEquals(username, userDetails.getUsername());
        assertEquals(firstName, userDetails.getFirstName());
        assertEquals(lastName, userDetails.getLastName());
        assertEquals(admin, userDetails.getAdmin());
        assertEquals(password, userDetails.getPassword());

        // Additional assertions for UserDetails interface methods
        assertTrue(userDetails.getAuthorities().isEmpty());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }
}
