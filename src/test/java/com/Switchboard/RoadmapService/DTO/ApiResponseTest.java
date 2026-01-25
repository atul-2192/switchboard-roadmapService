package com.Switchboard.RoadmapService.DTO;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void testConstructor_AllArgs() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        
        // Act
        ApiResponse response = new ApiResponse(
                true,
                "Success message",
                "test data",
                null,
                timestamp,
                "/api/test"
        );

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Success message", response.getMessage());
        assertEquals("test data", response.getData());
        assertNull(response.getErrorCode());
        assertEquals(timestamp, response.getTimestamp());
        assertEquals("/api/test", response.getPath());
    }

    @Test
    void testConstructor_NoArgs() {
        // Act
        ApiResponse response = new ApiResponse();

        // Assert
        assertFalse(response.isSuccess());
        assertNull(response.getMessage());
        assertNull(response.getData());
        assertNull(response.getErrorCode());
        assertNull(response.getTimestamp());
        assertNull(response.getPath());
    }

    @Test
    void testResponse_WithMessageAndSuccess() {
        // Act
        ApiResponse response = ApiResponse.response("Operation successful", true);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Operation successful", response.getMessage());
        assertNull(response.getData());
        assertNull(response.getErrorCode());
        assertNotNull(response.getTimestamp());
        assertNull(response.getPath());
    }

    @Test
    void testResponse_WithMessageDataAndPath() {
        // Act
        ApiResponse response = ApiResponse.response("Data retrieved", "test data", "/api/data");

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Data retrieved", response.getMessage());
        assertEquals("test data", response.getData());
        assertNull(response.getErrorCode());
        assertNotNull(response.getTimestamp());
        assertEquals("/api/data", response.getPath());
    }

    @Test
    void testError_WithMessageErrorCodeAndPath() {
        // Act
        ApiResponse response = ApiResponse.error("Error occurred", "ERR_001", "/api/error");

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Error occurred", response.getMessage());
        assertNull(response.getData());
        assertEquals("ERR_001", response.getErrorCode());
        assertNotNull(response.getTimestamp());
        assertEquals("/api/error", response.getPath());
    }

    @Test
    void testSetters() {
        // Arrange
        ApiResponse response = new ApiResponse();
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        response.setSuccess(true);
        response.setMessage("Updated message");
        response.setData("updated data");
        response.setErrorCode("ERR_002");
        response.setTimestamp(timestamp);
        response.setPath("/api/updated");

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Updated message", response.getMessage());
        assertEquals("updated data", response.getData());
        assertEquals("ERR_002", response.getErrorCode());
        assertEquals(timestamp, response.getTimestamp());
        assertEquals("/api/updated", response.getPath());
    }

    @Test
    void testGetters() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        ApiResponse response = new ApiResponse(
                false,
                "Test message",
                null,
                "TEST_ERROR",
                timestamp,
                "/test/path"
        );

        // Act & Assert
        assertFalse(response.isSuccess());
        assertEquals("Test message", response.getMessage());
        assertNull(response.getData());
        assertEquals("TEST_ERROR", response.getErrorCode());
        assertEquals(timestamp, response.getTimestamp());
        assertEquals("/test/path", response.getPath());
    }
}
