package com.Switchboard.RoadmapService.Service;

import com.Switchboard.RoadmapService.DTO.*;
import com.Switchboard.RoadmapService.Entity.RoadMapAssignment;
import com.Switchboard.RoadmapService.Entity.Task;
import com.Switchboard.RoadmapService.Feign.WorkspaceClient;
import com.Switchboard.RoadmapService.Mapper.RoadMapMapper;
import com.Switchboard.RoadmapService.Repository.RoadMapRepository;
import com.Switchboard.RoadmapService.Repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoadMapServiceTest {

    @Mock
    private RoadMapRepository roadMapRepository;

    @Mock
    private RoadMapMapper mapper;

    @Mock
    private WorkspaceClient workspaceClient;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private RoadMapService roadMapService;

    private UUID testId;
    private RoadMapAssignment assignment;
    private RoadMapAssignmentRequestDTO requestDTO;
    private RoadMapAssignmentResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();

        // Create test assignment
        assignment = RoadMapAssignment.builder()
                .id(testId)
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .tasks(new ArrayList<>())
                .build();

        // Create request DTO
        requestDTO = RoadMapAssignmentRequestDTO.builder()
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .tasks(new ArrayList<>())
                .build();

        // Create response DTO
        responseDTO = RoadMapAssignmentResponseDTO.builder()
                .id(testId)
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .tasks(new ArrayList<>())
                .build();
    }

    @Test
    void testSaveAssignment_Success() {
        // Arrange
        when(mapper.toRoadMapAssignmentEntity(any(RoadMapAssignmentRequestDTO.class)))
                .thenReturn(assignment);
        when(roadMapRepository.save(any(RoadMapAssignment.class)))
                .thenReturn(assignment);
        when(mapper.toRoadMapAssignmentResponseDTO(any(RoadMapAssignment.class)))
                .thenReturn(responseDTO);

        // Act
        RoadMapAssignmentResponseDTO result = roadMapService.saveAssignment(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals("Java Roadmap", result.getTitle());
        verify(mapper, times(1)).toRoadMapAssignmentEntity(any(RoadMapAssignmentRequestDTO.class));
        verify(roadMapRepository, times(1)).save(any(RoadMapAssignment.class));
        verify(mapper, times(1)).toRoadMapAssignmentResponseDTO(any(RoadMapAssignment.class));
    }

    @Test
    void testSaveAssignment_ThrowsException() {
        // Arrange
        when(mapper.toRoadMapAssignmentEntity(any(RoadMapAssignmentRequestDTO.class)))
                .thenReturn(assignment);
        when(roadMapRepository.save(any(RoadMapAssignment.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roadMapService.saveAssignment(requestDTO));
        verify(roadMapRepository, times(1)).save(any(RoadMapAssignment.class));
    }

    @Test
    void testGetAllAssignments_Success() {
        // Arrange
        List<RoadMapAssignment> assignments = Arrays.asList(assignment);
        List<RoadMapAssignmentResponseDTO> responseDTOs = Arrays.asList(responseDTO);
        
        when(roadMapRepository.findAll()).thenReturn(assignments);
        when(mapper.toRoadMapAssignmentResponseDTOList(anyList())).thenReturn(responseDTOs);

        // Act
        List<RoadMapAssignmentResponseDTO> result = roadMapService.getAllAssignments();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java Roadmap", result.get(0).getTitle());
        verify(roadMapRepository, times(1)).findAll();
        verify(mapper, times(1)).toRoadMapAssignmentResponseDTOList(anyList());
    }

    @Test
    void testGetAllAssignments_EmptyList() {
        // Arrange
        when(roadMapRepository.findAll()).thenReturn(new ArrayList<>());
        when(mapper.toRoadMapAssignmentResponseDTOList(anyList())).thenReturn(new ArrayList<>());

        // Act
        List<RoadMapAssignmentResponseDTO> result = roadMapService.getAllAssignments();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roadMapRepository, times(1)).findAll();
    }

    @Test
    void testGetAllAssignments_ThrowsException() {
        // Arrange
        when(roadMapRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roadMapService.getAllAssignments());
        verify(roadMapRepository, times(1)).findAll();
    }

    @Test
    void testGetAssignmentById_Found() {
        // Arrange
        when(roadMapRepository.findById(testId)).thenReturn(Optional.of(assignment));
        when(mapper.toRoadMapAssignmentResponseDTO(any(RoadMapAssignment.class)))
                .thenReturn(responseDTO);

        // Act
        Optional<RoadMapAssignmentResponseDTO> result = roadMapService.getAssignmentById(testId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testId, result.get().getId());
        assertEquals("Java Roadmap", result.get().getTitle());
        verify(roadMapRepository, times(1)).findById(testId);
        verify(mapper, times(1)).toRoadMapAssignmentResponseDTO(any(RoadMapAssignment.class));
    }

    @Test
    void testGetAssignmentById_NotFound() {
        // Arrange
        when(roadMapRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        Optional<RoadMapAssignmentResponseDTO> result = roadMapService.getAssignmentById(testId);

        // Assert
        assertFalse(result.isPresent());
        verify(roadMapRepository, times(1)).findById(testId);
        verify(mapper, never()).toRoadMapAssignmentResponseDTO(any(RoadMapAssignment.class));
    }

    @Test
    void testGetAssignmentById_ThrowsException() {
        // Arrange
        when(roadMapRepository.findById(testId)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roadMapService.getAssignmentById(testId));
        verify(roadMapRepository, times(1)).findById(testId);
    }

    @Test
    void testDeleteAssignment_Success() {
        // Arrange
        doNothing().when(roadMapRepository).deleteById(testId);

        // Act
        roadMapService.deleteAssignment(testId);

        // Assert
        verify(roadMapRepository, times(1)).deleteById(testId);
    }

    @Test
    void testDeleteAssignment_ThrowsException() {
        // Arrange
        doThrow(new RuntimeException("Database error")).when(roadMapRepository).deleteById(testId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roadMapService.deleteAssignment(testId));
        verify(roadMapRepository, times(1)).deleteById(testId);
    }

    @Test
    void testAddAssignmentRoadmap_Success() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID taskId1 = UUID.randomUUID();
        UUID taskId2 = UUID.randomUUID();
        
        AddAssignmentToWorkspaceRequestDTO request = new AddAssignmentToWorkspaceRequestDTO();
        request.setAssignmentId(testId);
        request.setTaskIds(Arrays.asList(taskId1, taskId2));

        Task task1 = Task.builder()
                .id(taskId1)
                .title("Task 1")
                .build();
        
        Task task2 = Task.builder()
                .id(taskId2)
                .title("Task 2")
                .build();

        List<Task> tasks = Arrays.asList(task1, task2);
        assignment.setTasks(tasks);

        TaskResponseDTO taskDTO1 = TaskResponseDTO.builder()
                .id(taskId1)
                .title("Task 1")
                .build();
        
        TaskResponseDTO taskDTO2 = TaskResponseDTO.builder()
                .id(taskId2)
                .title("Task 2")
                .build();

        List<TaskResponseDTO> taskDTOs = Arrays.asList(taskDTO1, taskDTO2);

        ApiResponse apiResponse = ApiResponse.response("Success", true);

        when(roadMapRepository.findById(testId)).thenReturn(Optional.of(assignment));
        when(taskRepository.findAllById(anyList())).thenReturn(tasks);
        when(mapper.toRoadMapAssignmentResponseDTO(any(RoadMapAssignment.class))).thenReturn(responseDTO);
        when(mapper.toTaskResponseDTOList(anyList())).thenReturn(taskDTOs);
        when(workspaceClient.addRoadmapAssignmentToWorkspace(any(RoadMapAssignmentResponseDTO.class), eq(userId)))
                .thenReturn(apiResponse);

        // Act
        ApiResponse result = roadMapService.addAssignmentRoadmap(request, userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isSuccess());
        verify(roadMapRepository, times(1)).findById(testId);
        verify(taskRepository, times(1)).findAllById(anyList());
        verify(workspaceClient, times(1)).addRoadmapAssignmentToWorkspace(any(RoadMapAssignmentResponseDTO.class), eq(userId));
    }

    @Test
    void testAddAssignmentRoadmap_AssignmentNotFound() {
        // Arrange
        UUID userId = UUID.randomUUID();
        AddAssignmentToWorkspaceRequestDTO request = new AddAssignmentToWorkspaceRequestDTO();
        request.setAssignmentId(testId);
        request.setTaskIds(Arrays.asList(UUID.randomUUID()));

        when(roadMapRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        ApiResponse result = roadMapService.addAssignmentRoadmap(request, userId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals("Assignment not found", result.getMessage());
        verify(roadMapRepository, times(1)).findById(testId);
        verify(taskRepository, never()).findAllById(anyList());
        verify(workspaceClient, never()).addRoadmapAssignmentToWorkspace(any(), any());
    }

    @Test
    void testAddAssignmentRoadmap_NoTasksFound() {
        // Arrange
        UUID userId = UUID.randomUUID();
        AddAssignmentToWorkspaceRequestDTO request = new AddAssignmentToWorkspaceRequestDTO();
        request.setAssignmentId(testId);
        request.setTaskIds(Arrays.asList(UUID.randomUUID()));

        when(roadMapRepository.findById(testId)).thenReturn(Optional.of(assignment));
        when(taskRepository.findAllById(anyList())).thenReturn(new ArrayList<>());

        // Act
        ApiResponse result = roadMapService.addAssignmentRoadmap(request, userId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals("No tasks found for provided taskIds", result.getMessage());
        verify(roadMapRepository, times(1)).findById(testId);
        verify(taskRepository, times(1)).findAllById(anyList());
        verify(workspaceClient, never()).addRoadmapAssignmentToWorkspace(any(), any());
    }

    @Test
    void testAddAssignmentRoadmap_PartialTasksFound() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID taskId1 = UUID.randomUUID();
        UUID taskId2 = UUID.randomUUID();
        UUID taskId3 = UUID.randomUUID();
        
        AddAssignmentToWorkspaceRequestDTO request = new AddAssignmentToWorkspaceRequestDTO();
        request.setAssignmentId(testId);
        request.setTaskIds(Arrays.asList(taskId1, taskId2, taskId3));

        Task task1 = Task.builder().id(taskId1).title("Task 1").build();
        List<Task> tasks = Arrays.asList(task1); // Only 1 task found instead of 3

        assignment.setTasks(tasks);

        TaskResponseDTO taskDTO1 = TaskResponseDTO.builder().id(taskId1).title("Task 1").build();
        List<TaskResponseDTO> taskDTOs = Arrays.asList(taskDTO1);

        ApiResponse apiResponse = ApiResponse.response("Success", true);

        when(roadMapRepository.findById(testId)).thenReturn(Optional.of(assignment));
        when(taskRepository.findAllById(anyList())).thenReturn(tasks);
        when(mapper.toRoadMapAssignmentResponseDTO(any(RoadMapAssignment.class))).thenReturn(responseDTO);
        when(mapper.toTaskResponseDTOList(anyList())).thenReturn(taskDTOs);
        when(workspaceClient.addRoadmapAssignmentToWorkspace(any(RoadMapAssignmentResponseDTO.class), eq(userId)))
                .thenReturn(apiResponse);

        // Act
        ApiResponse result = roadMapService.addAssignmentRoadmap(request, userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isSuccess());
        verify(roadMapRepository, times(1)).findById(testId);
        verify(taskRepository, times(1)).findAllById(anyList());
        verify(workspaceClient, times(1)).addRoadmapAssignmentToWorkspace(any(RoadMapAssignmentResponseDTO.class), eq(userId));
    }
}
