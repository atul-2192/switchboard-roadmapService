package com.Switchboard.RoadmapService.Exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void testConstructor_WithMessage() {
        // Arrange
        String message = "Assignment not found with id: 123";

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testThrowException() {
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException("Resource does not exist");
        });
    }

    @Test
    void testExceptionInheritance() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException("Test message");

        // Act & Assert
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionMessage() {
        // Arrange
        String expectedMessage = "Task with id 456 not found";
        
        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
}
