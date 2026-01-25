package com.Switchboard.RoadmapService.Exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnauthorizedExceptionTest {

    @Test
    void testConstructor_WithMessage() {
        // Arrange
        String message = "User is not authorized to access this resource";

        // Act
        UnauthorizedException exception = new UnauthorizedException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testThrowException() {
        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> {
            throw new UnauthorizedException("Unauthorized");
        });
    }

    @Test
    void testExceptionInheritance() {
        // Arrange
        UnauthorizedException exception = new UnauthorizedException("Test message");

        // Act & Assert
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionMessage() {
        // Arrange
        String expectedMessage = "Access denied for user";
        
        // Act
        UnauthorizedException exception = new UnauthorizedException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
}
