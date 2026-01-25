package com.Switchboard.RoadmapService.Controller;

import com.Switchboard.RoadmapService.DTO.TaskRequestDTO;
import com.Switchboard.RoadmapService.DTO.TaskResponseDTO;
import com.Switchboard.RoadmapService.Service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private UUID testTaskId;
    private UUID testAssignmentId;
    private TaskRequestDTO requestDTO;
    private TaskResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        testTaskId = UUID.randomUUID();
        testAssignmentId = UUID.randomUUID();

        // Arrange - Create test data
        requestDTO = TaskRequestDTO.builder()
                .title("Learn Java Basics")
                .description("Complete Java fundamentals")
                .rewardPoints(100)
                .titleColor("#FF5733")
                .estimatedHours(40.0)
                .build();

        responseDTO = TaskResponseDTO.builder()
                .id(testTaskId)
                .title("Learn Java Basics")
                .description("Complete Java fundamentals")
                .rewardPoints(100)
                .titleColor("#FF5733")
                .estimatedHours(40.0)
                .build();
    }

    @Test
    void testAddTask_Success() {
        // Arrange
        when(taskService.addTaskToAssignment(eq(testAssignmentId), any(TaskRequestDTO.class)))
                .thenReturn(responseDTO);

        // Act
        ResponseEntity<TaskResponseDTO> response = taskController.addTask(testAssignmentId, requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testTaskId, response.getBody().getId());
        assertEquals("Learn Java Basics", response.getBody().getTitle());
        assertEquals(100, response.getBody().getRewardPoints());
        verify(taskService, times(1)).addTaskToAssignment(eq(testAssignmentId), any(TaskRequestDTO.class));
    }

    @Test
    void testUpdateTask_Success() {
        // Arrange
        when(taskService.updateTask(eq(testTaskId), any(TaskRequestDTO.class)))
                .thenReturn(responseDTO);

        // Act
        ResponseEntity<TaskResponseDTO> response = taskController.updateTask(testTaskId, requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testTaskId, response.getBody().getId());
        assertEquals("Learn Java Basics", response.getBody().getTitle());
        verify(taskService, times(1)).updateTask(eq(testTaskId), any(TaskRequestDTO.class));
    }

    @Test
    void testDeleteTask_Success() {
        // Arrange
        doNothing().when(taskService).deleteTask(testTaskId);

        // Act
        ResponseEntity<Void> response = taskController.deleteTask(testTaskId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(testTaskId);
    }

    @Test
    void testGetTasksByAssignment_Success() {
        // Arrange
        List<TaskResponseDTO> tasks = Arrays.asList(responseDTO);
        when(taskService.getTasksByAssignment(testAssignmentId))
                .thenReturn(tasks);

        // Act
        ResponseEntity<List<TaskResponseDTO>> response = taskController.getTasksByAssignment(testAssignmentId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Learn Java Basics", response.getBody().get(0).getTitle());
        verify(taskService, times(1)).getTasksByAssignment(testAssignmentId);
    }

    @Test
    void testGetTasksByAssignment_EmptyList() {
        // Arrange
        when(taskService.getTasksByAssignment(testAssignmentId))
                .thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<TaskResponseDTO>> response = taskController.getTasksByAssignment(testAssignmentId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(taskService, times(1)).getTasksByAssignment(testAssignmentId);
    }
}
