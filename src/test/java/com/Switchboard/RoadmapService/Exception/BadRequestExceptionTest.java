package com.Switchboard.RoadmapService.Exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BadRequestExceptionTest {

    @Test
    void testConstructor_WithMessage() {
        // Arrange
        String message = "Invalid input provided";

        // Act
        BadRequestException exception = new BadRequestException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testThrowException() {
        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            throw new BadRequestException("Bad request");
        });
    }

    @Test
    void testExceptionInheritance() {
        // Arrange
        BadRequestException exception = new BadRequestException("Test message");

        // Act & Assert
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionMessage() {
        // Arrange
        String expectedMessage = "Request validation failed";
        
        // Act
        BadRequestException exception = new BadRequestException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
}
