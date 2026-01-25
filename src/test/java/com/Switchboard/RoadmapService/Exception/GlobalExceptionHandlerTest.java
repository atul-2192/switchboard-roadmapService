package com.Switchboard.RoadmapService.Exception;

import com.Switchboard.RoadmapService.DTO.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        when(request.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    void testHandleResourceNotFound() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        // Act
        ResponseEntity<ApiResponse> response = globalExceptionHandler.handleResourceNotFound(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Resource not found", response.getBody().getMessage());
        assertEquals("RESOURCE_NOT_FOUND", response.getBody().getErrorCode());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void testHandleBadRequest() {
        // Arrange
        BadRequestException exception = new BadRequestException("Invalid request");

        // Act
        ResponseEntity<ApiResponse> response = globalExceptionHandler.handleBadRequest(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Invalid request", response.getBody().getMessage());
        assertEquals("BAD_REQUEST", response.getBody().getErrorCode());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void testHandleUnauthorized() {
        // Arrange
        UnauthorizedException exception = new UnauthorizedException("Unauthorized access");

        // Act
        ResponseEntity<ApiResponse> response = globalExceptionHandler.handleUnauthorized(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Unauthorized access", response.getBody().getMessage());
        assertEquals("UNAUTHORIZED", response.getBody().getErrorCode());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void testHandleUnexpected() {
        // Arrange
        UnexpectedException exception = new UnexpectedException("Unexpected error occurred");

        // Act
        ResponseEntity<ApiResponse> response = globalExceptionHandler.handleUnexpected(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Unexpected error occurred", response.getBody().getMessage());
        assertEquals("UNEXPECTED_ERROR", response.getBody().getErrorCode());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void testHandleValidation() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "Validation failed");
        when(bindingResult.getFieldError()).thenReturn(fieldError);
        
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<ApiResponse> response = globalExceptionHandler.handleValidation(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Validation failed", response.getBody().getMessage());
        assertEquals("VALIDATION_ERROR", response.getBody().getErrorCode());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void testHandleGlobalException() {
        // Arrange
        Exception exception = new Exception("Unexpected system error");

        // Act
        ResponseEntity<ApiResponse> response = globalExceptionHandler.handleGlobalException(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertTrue(response.getBody().getMessage().contains("Internal Server Error"));
        assertTrue(response.getBody().getMessage().contains("Unexpected system error"));
        assertEquals("INTERNAL_SERVER_ERROR", response.getBody().getErrorCode());
        assertEquals("/api/test", response.getBody().getPath());
    }
}
