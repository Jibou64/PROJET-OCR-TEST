package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

    public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("john.doe@example.com");
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setPassword("password123");
        userDto.setAdmin(true);

        // Act
        User user = userMapper.toEntity(userDto);

        // Assert
        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.isAdmin(), user.isAdmin());
    }

    @Test
    void testToDto() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("john.doe@example.com");
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setPassword("password123");
        user.setAdmin(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Act
        UserDto userDto = userMapper.toDto(user);

        // Assert
        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.isAdmin(), userDto.isAdmin());
    }
}
