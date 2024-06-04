package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtResponseTest {

    @Test
    void testJwtResponseConstructorAndGetters() {
        // Arrange
        String accessToken = "dummyAccessToken";
        Long id = 1L;
        String username = "john_doe";
        String firstName = "John";
        String lastName = "Doe";
        Boolean admin = true;

        // Act
        JwtResponse jwtResponse = new JwtResponse(accessToken, id, username, firstName, lastName, admin);

        // Assert
        assertNotNull(jwtResponse);
        assertEquals(accessToken, jwtResponse.getToken());
        assertEquals("Bearer", jwtResponse.getType());
        assertEquals(id, jwtResponse.getId());
        assertEquals(username, jwtResponse.getUsername());
        assertEquals(firstName, jwtResponse.getFirstName());
        assertEquals(lastName, jwtResponse.getLastName());
        assertEquals(admin, jwtResponse.getAdmin());
    }

    @Test
    void testJwtResponseConstructorWithNonAdminUser() {
        // Arrange
        // Act
        JwtResponse jwtResponse = new JwtResponse("dummyAccessToken", 1L, "john_doe", "John", "Doe", false);

        // Assert
        assertNotNull(jwtResponse);
        assertEquals("dummyAccessToken", jwtResponse.getToken());
        assertEquals("Bearer", jwtResponse.getType());
        assertEquals(1L, jwtResponse.getId());
        assertEquals("john_doe", jwtResponse.getUsername());
        assertEquals("John", jwtResponse.getFirstName());
        assertEquals("Doe", jwtResponse.getLastName());
        assertEquals(false, jwtResponse.getAdmin()); // Change the expected admin value to false
    }
}
