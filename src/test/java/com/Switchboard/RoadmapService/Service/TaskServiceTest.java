package com.Switchboard.RoadmapService.Service;

import com.Switchboard.RoadmapService.DTO.TaskRequestDTO;
import com.Switchboard.RoadmapService.DTO.TaskResponseDTO;
import com.Switchboard.RoadmapService.Entity.RoadMapAssignment;
import com.Switchboard.RoadmapService.Entity.Task;
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
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private RoadMapRepository roadMapRepository;

    @Mock
    private RoadMapMapper mapper;

    @InjectMocks
    private TaskService taskService;

    private UUID testTaskId;
    private UUID testAssignmentId;
    private Task task;
    private TaskRequestDTO requestDTO;
    private TaskResponseDTO responseDTO;
    private RoadMapAssignment assignment;

    @BeforeEach
    void setUp() {
        testTaskId = UUID.randomUUID();
        testAssignmentId = UUID.randomUUID();

        // Create test task
        task = Task.builder()
                .id(testTaskId)
                .title("Learn Java Basics")
                .description("Complete Java fundamentals")
                .rewardPoints(100)
                .titleColor("#FF5733")
                .estimatedHours(40.0)
                .build();

        // Create request DTO
        requestDTO = TaskRequestDTO.builder()
                .title("Learn Java Basics")
                .description("Complete Java fundamentals")
                .rewardPoints(100)
                .titleColor("#FF5733")
                .estimatedHours(40.0)
                .build();

        // Create response DTO
        responseDTO = TaskResponseDTO.builder()
                .id(testTaskId)
                .title("Learn Java Basics")
                .description("Complete Java fundamentals")
                .rewardPoints(100)
                .titleColor("#FF5733")
                .estimatedHours(40.0)
                .build();

        // Create assignment
        assignment = RoadMapAssignment.builder()
                .id(testAssignmentId)
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .tasks(new ArrayList<>(Arrays.asList(task)))
                .build();
    }

    @Test
    void testAddTaskToAssignment_Success() {
        // Arrange
        when(roadMapRepository.findById(testAssignmentId)).thenReturn(Optional.of(assignment));
        when(mapper.toTaskEntity(any(TaskRequestDTO.class))).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(mapper.toTaskResponseDTO(any(Task.class))).thenReturn(responseDTO);

        // Act
        TaskResponseDTO result = taskService.addTaskToAssignment(testAssignmentId, requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testTaskId, result.getId());
        assertEquals("Learn Java Basics", result.getTitle());
        assertEquals(100, result.getRewardPoints());
        verify(roadMapRepository, times(1)).findById(testAssignmentId);
        verify(mapper, times(1)).toTaskEntity(any(TaskRequestDTO.class));
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(mapper, times(1)).toTaskResponseDTO(any(Task.class));
    }

    @Test
    void testAddTaskToAssignment_AssignmentNotFound() {
        // Arrange
        when(roadMapRepository.findById(testAssignmentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
                () -> taskService.addTaskToAssignment(testAssignmentId, requestDTO));
        
        assertTrue(exception.getMessage().contains("Assignment not found"));
        verify(roadMapRepository, times(1)).findById(testAssignmentId);
        verify(mapper, never()).toTaskEntity(any(TaskRequestDTO.class));
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testUpdateTask_Success() {
        // Arrange
        when(taskRepository.findById(testTaskId)).thenReturn(Optional.of(task));
        doNothing().when(mapper).updateTaskFromDTO(any(Task.class), any(TaskRequestDTO.class));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(mapper.toTaskResponseDTO(any(Task.class))).thenReturn(responseDTO);

        // Act
        TaskResponseDTO result = taskService.updateTask(testTaskId, requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testTaskId, result.getId());
        assertEquals("Learn Java Basics", result.getTitle());
        verify(taskRepository, times(1)).findById(testTaskId);
        verify(mapper, times(1)).updateTaskFromDTO(any(Task.class), any(TaskRequestDTO.class));
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(mapper, times(1)).toTaskResponseDTO(any(Task.class));
    }

    @Test
    void testUpdateTask_TaskNotFound() {
        // Arrange
        when(taskRepository.findById(testTaskId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
                () -> taskService.updateTask(testTaskId, requestDTO));
        
        assertTrue(exception.getMessage().contains("Task not found"));
        verify(taskRepository, times(1)).findById(testTaskId);
        verify(mapper, never()).updateTaskFromDTO(any(Task.class), any(TaskRequestDTO.class));
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testDeleteTask_Success() {
        // Arrange
        when(taskRepository.existsById(testTaskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(testTaskId);

        // Act
        taskService.deleteTask(testTaskId);

        // Assert
        verify(taskRepository, times(1)).existsById(testTaskId);
        verify(taskRepository, times(1)).deleteById(testTaskId);
    }

    @Test
    void testDeleteTask_TaskNotFound() {
        // Arrange
        when(taskRepository.existsById(testTaskId)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
                () -> taskService.deleteTask(testTaskId));
        
        assertTrue(exception.getMessage().contains("Task not found"));
        verify(taskRepository, times(1)).existsById(testTaskId);
        verify(taskRepository, never()).deleteById(testTaskId);
    }

    @Test
    void testGetTasksByAssignment_Success() {
        // Arrange
        List<TaskResponseDTO> taskDTOs = Arrays.asList(responseDTO);
        
        when(roadMapRepository.findById(testAssignmentId)).thenReturn(Optional.of(assignment));
        when(mapper.toTaskResponseDTOList(anyList())).thenReturn(taskDTOs);

        // Act
        List<TaskResponseDTO> result = taskService.getTasksByAssignment(testAssignmentId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Learn Java Basics", result.get(0).getTitle());
        verify(roadMapRepository, times(1)).findById(testAssignmentId);
        verify(mapper, times(1)).toTaskResponseDTOList(anyList());
    }

    @Test
    void testGetTasksByAssignment_AssignmentNotFound() {
        // Arrange
        when(roadMapRepository.findById(testAssignmentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
                () -> taskService.getTasksByAssignment(testAssignmentId));
        
        assertTrue(exception.getMessage().contains("Assignment not found"));
        verify(roadMapRepository, times(1)).findById(testAssignmentId);
        verify(mapper, never()).toTaskResponseDTOList(anyList());
    }

    @Test
    void testGetTasksByAssignment_EmptyTasks() {
        // Arrange
        assignment.setTasks(new ArrayList<>());
        when(roadMapRepository.findById(testAssignmentId)).thenReturn(Optional.of(assignment));
        when(mapper.toTaskResponseDTOList(anyList())).thenReturn(new ArrayList<>());

        // Act
        List<TaskResponseDTO> result = taskService.getTasksByAssignment(testAssignmentId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roadMapRepository, times(1)).findById(testAssignmentId);
        verify(mapper, times(1)).toTaskResponseDTOList(anyList());
    }
}
