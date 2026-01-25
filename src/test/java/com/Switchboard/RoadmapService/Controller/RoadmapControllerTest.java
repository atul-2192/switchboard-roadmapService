package com.Switchboard.RoadmapService.Controller;

import com.Switchboard.RoadmapService.DTO.AddAssignmentToWorkspaceRequestDTO;
import com.Switchboard.RoadmapService.DTO.ApiResponse;
import com.Switchboard.RoadmapService.DTO.RoadMapAssignmentRequestDTO;
import com.Switchboard.RoadmapService.DTO.RoadMapAssignmentResponseDTO;
import com.Switchboard.RoadmapService.Service.RoadMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoadmapControllerTest {

    @Mock
    private RoadMapService roadMapService;

    @InjectMocks
    private RoadmapController roadmapController;

    private RoadMapAssignmentRequestDTO requestDTO;
    private RoadMapAssignmentResponseDTO responseDTO;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        
        // Arrange - Create test data
        requestDTO = RoadMapAssignmentRequestDTO.builder()
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .tasks(new ArrayList<>())
                .build();

        responseDTO = RoadMapAssignmentResponseDTO.builder()
                .id(testId)
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .tasks(new ArrayList<>())
                .build();
    }

    @Test
    void testCreateAssignment_Success() {
        // Arrange
        when(roadMapService.saveAssignment(any(RoadMapAssignmentRequestDTO.class)))
                .thenReturn(responseDTO);

        // Act
        ResponseEntity<RoadMapAssignmentResponseDTO> response = roadmapController.createAssignment(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testId, response.getBody().getId());
        assertEquals("Java Roadmap", response.getBody().getTitle());
        verify(roadMapService, times(1)).saveAssignment(any(RoadMapAssignmentRequestDTO.class));
    }

    @Test
    void testGetAllAssignments_Success() {
        // Arrange
        List<RoadMapAssignmentResponseDTO> assignments = Arrays.asList(responseDTO);
        when(roadMapService.getAllAssignments()).thenReturn(assignments);

        // Act
        List<RoadMapAssignmentResponseDTO> result = roadmapController.getAllAssignments();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java Roadmap", result.get(0).getTitle());
        verify(roadMapService, times(1)).getAllAssignments();
    }

    @Test
    void testGetAllAssignments_EmptyList() {
        // Arrange
        when(roadMapService.getAllAssignments()).thenReturn(new ArrayList<>());

        // Act
        List<RoadMapAssignmentResponseDTO> result = roadmapController.getAllAssignments();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roadMapService, times(1)).getAllAssignments();
    }

    @Test
    void testGetAssignmentById_Found() {
        // Arrange
        when(roadMapService.getAssignmentById(testId))
                .thenReturn(Optional.of(responseDTO));

        // Act
        ResponseEntity<RoadMapAssignmentResponseDTO> response = roadmapController.getAssignmentById(testId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testId, response.getBody().getId());
        verify(roadMapService, times(1)).getAssignmentById(testId);
    }

    @Test
    void testGetAssignmentById_NotFound() {
        // Arrange
        when(roadMapService.getAssignmentById(testId))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<RoadMapAssignmentResponseDTO> response = roadmapController.getAssignmentById(testId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(roadMapService, times(1)).getAssignmentById(testId);
    }

    @Test
    void testDeleteAssignment_Success() {
        // Arrange
        doNothing().when(roadMapService).deleteAssignment(testId);

        // Act
        ResponseEntity<Void> response = roadmapController.deleteAssignment(testId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(roadMapService, times(1)).deleteAssignment(testId);
    }

    @Test
    void testAddRoadmapAssignmentToWorkspace_Success() {
        // Arrange
        UUID userId = UUID.randomUUID();
        AddAssignmentToWorkspaceRequestDTO request = new AddAssignmentToWorkspaceRequestDTO();
        request.setAssignmentId(testId);
        request.setTaskIds(Arrays.asList(UUID.randomUUID()));
        
        ApiResponse apiResponse = ApiResponse.response("Success", true);
        when(roadMapService.addAssignmentRoadmap(any(AddAssignmentToWorkspaceRequestDTO.class), eq(userId)))
                .thenReturn(apiResponse);

        // Act
        ResponseEntity<String> response = roadmapController.addRoadmapAssignmentToWorkspace(
                request, userId.toString());

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Roadmap assignment added to workspace successfully.", response.getBody());
        verify(roadMapService, times(1)).addAssignmentRoadmap(any(AddAssignmentToWorkspaceRequestDTO.class), eq(userId));
    }
}
