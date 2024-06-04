package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }


    void testFindByIdSuccess() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userService.findById(userId)).thenReturn(user);

        // When
        ResponseEntity<?> response = userController.findById(userId.toString());

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        Long userId = 1L;
        when(userService.findById(userId)).thenReturn(null);

        // When
        ResponseEntity<?> response = userController.findById(userId.toString());

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).findById(userId);
    }

    @Test
    void testFindByIdBadRequest() {
        // When
        ResponseEntity<?> response = userController.findById("invalid_id");

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDeleteUserSuccess() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        UserDetails userDetails = mock(UserDetails.class);

        when(userService.findById(userId)).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        // When
        ResponseEntity<?> response = userController.save(userId.toString());

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).delete(userId);
    }

    @Test
    void testDeleteUserNotFound() {
        // Given
        Long userId = 1L;
        when(userService.findById(userId)).thenReturn(null);

        // When
        ResponseEntity<?> response = userController.save(userId.toString());

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, never()).delete(anyLong());
    }

    @Test
    void testDeleteUserUnauthorized() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        UserDetails userDetails = mock(UserDetails.class);

        when(userService.findById(userId)).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("different@example.com");

        // When
        ResponseEntity<?> response = userController.save(userId.toString());

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(userService, never()).delete(anyLong());
    }

    @Test
    void testDeleteUserBadRequest() {
        // When
        ResponseEntity<?> response = userController.save("invalid_id");

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
