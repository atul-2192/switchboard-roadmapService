package com.Switchboard.RoadmapService.Mapper;

import com.Switchboard.RoadmapService.DTO.RoadMapAssignmentRequestDTO;
import com.Switchboard.RoadmapService.DTO.RoadMapAssignmentResponseDTO;
import com.Switchboard.RoadmapService.DTO.TaskRequestDTO;
import com.Switchboard.RoadmapService.DTO.TaskResponseDTO;
import com.Switchboard.RoadmapService.Entity.RoadMapAssignment;
import com.Switchboard.RoadmapService.Entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoadMapMapperTest {

    @InjectMocks
    private RoadMapMapper roadMapMapper;

    private UUID testTaskId;
    private UUID testAssignmentId;
    private Task task;
    private TaskRequestDTO taskRequestDTO;
    private RoadMapAssignment assignment;
    private RoadMapAssignmentRequestDTO assignmentRequestDTO;

    @BeforeEach
    void setUp() {
        testTaskId = UUID.randomUUID();
        testAssignmentId = UUID.randomUUID();

        // Create test task entity
        task = Task.builder()
                .id(testTaskId)
                .title("Learn Java Basics")
                .description("Complete Java fundamentals")
                .rewardPoints(100)
                .titleColor("#FF5733")
                .estimatedHours(40.0)
                .build();

        // Create task request DTO
        taskRequestDTO = TaskRequestDTO.builder()
                .title("Learn Java Basics")
                .description("Complete Java fundamentals")
                .rewardPoints(100)
                .titleColor("#FF5733")
                .estimatedHours(40.0)
                .build();

        // Create assignment entity
        assignment = RoadMapAssignment.builder()
                .id(testAssignmentId)
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .tasks(new ArrayList<>(Arrays.asList(task)))
                .build();

        // Create assignment request DTO
        assignmentRequestDTO = RoadMapAssignmentRequestDTO.builder()
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .tasks(new ArrayList<>(Arrays.asList(taskRequestDTO)))
                .build();
    }

    @Test
    void testToTaskResponseDTO_Success() {
        // Act
        TaskResponseDTO result = roadMapMapper.toTaskResponseDTO(task);

        // Assert
        assertNotNull(result);
        assertEquals(testTaskId, result.getId());
        assertEquals("Learn Java Basics", result.getTitle());
        assertEquals("Complete Java fundamentals", result.getDescription());
        assertEquals(100, result.getRewardPoints());
        assertEquals("#FF5733", result.getTitleColor());
        assertEquals(40.0, result.getEstimatedHours());
    }

    @Test
    void testToTaskEntity_Success() {
        // Act
        Task result = roadMapMapper.toTaskEntity(taskRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Learn Java Basics", result.getTitle());
        assertEquals("Complete Java fundamentals", result.getDescription());
        assertEquals(100, result.getRewardPoints());
        assertEquals("#FF5733", result.getTitleColor());
        assertEquals(40.0, result.getEstimatedHours());
    }

    @Test
    void testUpdateTaskFromDTO_Success() {
        // Arrange
        Task existingTask = Task.builder()
                .id(testTaskId)
                .title("Old Title")
                .description("Old Description")
                .rewardPoints(50)
                .titleColor("#000000")
                .estimatedHours(10.0)
                .build();

        // Act
        roadMapMapper.updateTaskFromDTO(existingTask, taskRequestDTO);

        // Assert
        assertEquals("Learn Java Basics", existingTask.getTitle());
        assertEquals("Complete Java fundamentals", existingTask.getDescription());
        assertEquals(100, existingTask.getRewardPoints());
        assertEquals("#FF5733", existingTask.getTitleColor());
        assertEquals(40.0, existingTask.getEstimatedHours());
    }

    @Test
    void testToRoadMapAssignmentResponseDTO_Success() {
        // Act
        RoadMapAssignmentResponseDTO result = roadMapMapper.toRoadMapAssignmentResponseDTO(assignment);

        // Assert
        assertNotNull(result);
        assertEquals(testAssignmentId, result.getId());
        assertEquals("Java Roadmap", result.getTitle());
        assertEquals("Complete Java Learning Path", result.getDescription());
        assertNotNull(result.getTasks());
        assertEquals(1, result.getTasks().size());
        assertEquals("Learn Java Basics", result.getTasks().get(0).getTitle());
    }

    @Test
    void testToRoadMapAssignmentResponseDTO_NullTasks() {
        // Arrange
        assignment.setTasks(null);

        // Act
        RoadMapAssignmentResponseDTO result = roadMapMapper.toRoadMapAssignmentResponseDTO(assignment);

        // Assert
        assertNotNull(result);
        assertEquals(testAssignmentId, result.getId());
        assertNotNull(result.getTasks());
        assertTrue(result.getTasks().isEmpty());
    }

    @Test
    void testToRoadMapAssignmentResponseDTO_EmptyTasks() {
        // Arrange
        assignment.setTasks(new ArrayList<>());

        // Act
        RoadMapAssignmentResponseDTO result = roadMapMapper.toRoadMapAssignmentResponseDTO(assignment);

        // Assert
        assertNotNull(result);
        assertEquals(testAssignmentId, result.getId());
        assertNotNull(result.getTasks());
        assertTrue(result.getTasks().isEmpty());
    }

    @Test
    void testToRoadMapAssignmentEntity_Success() {
        // Act
        RoadMapAssignment result = roadMapMapper.toRoadMapAssignmentEntity(assignmentRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Java Roadmap", result.getTitle());
        assertEquals("Complete Java Learning Path", result.getDescription());
        assertNotNull(result.getTasks());
        assertEquals(1, result.getTasks().size());
        assertEquals("Learn Java Basics", result.getTasks().get(0).getTitle());
    }

    @Test
    void testToRoadMapAssignmentEntity_NullTasks() {
        // Arrange
        assignmentRequestDTO.setTasks(null);

        // Act
        RoadMapAssignment result = roadMapMapper.toRoadMapAssignmentEntity(assignmentRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Java Roadmap", result.getTitle());
        assertNotNull(result.getTasks());
        assertTrue(result.getTasks().isEmpty());
    }

    @Test
    void testToRoadMapAssignmentEntity_EmptyTasks() {
        // Arrange
        assignmentRequestDTO.setTasks(new ArrayList<>());

        // Act
        RoadMapAssignment result = roadMapMapper.toRoadMapAssignmentEntity(assignmentRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Java Roadmap", result.getTitle());
        assertNotNull(result.getTasks());
        assertTrue(result.getTasks().isEmpty());
    }

    @Test
    void testUpdateRoadMapAssignmentFromDTO_Success() {
        // Arrange
        RoadMapAssignment existingAssignment = RoadMapAssignment.builder()
                .id(testAssignmentId)
                .title("Old Title")
                .description("Old Description")
                .tasks(new ArrayList<>())
                .build();

        // Act
        roadMapMapper.updateRoadMapAssignmentFromDTO(existingAssignment, assignmentRequestDTO);

        // Assert
        assertEquals("Java Roadmap", existingAssignment.getTitle());
        assertEquals("Complete Java Learning Path", existingAssignment.getDescription());
    }

    @Test
    void testToRoadMapAssignmentResponseDTOList_Success() {
        // Arrange
        RoadMapAssignment assignment2 = RoadMapAssignment.builder()
                .id(UUID.randomUUID())
                .title("Python Roadmap")
                .description("Complete Python Learning Path")
                .tasks(new ArrayList<>())
                .build();
        
        List<RoadMapAssignment> assignments = Arrays.asList(assignment, assignment2);

        // Act
        List<RoadMapAssignmentResponseDTO> result = roadMapMapper.toRoadMapAssignmentResponseDTOList(assignments);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Java Roadmap", result.get(0).getTitle());
        assertEquals("Python Roadmap", result.get(1).getTitle());
    }

    @Test
    void testToRoadMapAssignmentResponseDTOList_EmptyList() {
        // Arrange
        List<RoadMapAssignment> assignments = new ArrayList<>();

        // Act
        List<RoadMapAssignmentResponseDTO> result = roadMapMapper.toRoadMapAssignmentResponseDTOList(assignments);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToTaskResponseDTOList_Success() {
        // Arrange
        Task task2 = Task.builder()
                .id(UUID.randomUUID())
                .title("Advanced Java")
                .description("Learn advanced concepts")
                .rewardPoints(200)
                .titleColor("#0000FF")
                .estimatedHours(60.0)
                .build();
        
        List<Task> tasks = Arrays.asList(task, task2);

        // Act
        List<TaskResponseDTO> result = roadMapMapper.toTaskResponseDTOList(tasks);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Learn Java Basics", result.get(0).getTitle());
        assertEquals("Advanced Java", result.get(1).getTitle());
    }

    @Test
    void testToTaskResponseDTOList_EmptyList() {
        // Arrange
        List<Task> tasks = new ArrayList<>();

        // Act
        List<TaskResponseDTO> result = roadMapMapper.toTaskResponseDTOList(tasks);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
