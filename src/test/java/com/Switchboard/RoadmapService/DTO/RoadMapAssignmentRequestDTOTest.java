package com.Switchboard.RoadmapService.DTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoadMapAssignmentRequestDTOTest {

    @Test
    void testBuilder_AllFields() {
        // Arrange
        TaskRequestDTO task = TaskRequestDTO.builder()
                .title("Task 1")
                .build();
        List<TaskRequestDTO> tasks = new ArrayList<>(Arrays.asList(task));

        // Act
        RoadMapAssignmentRequestDTO dto = RoadMapAssignmentRequestDTO.builder()
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .tasks(tasks)
                .build();

        // Assert
        assertNotNull(dto);
        assertEquals("Java Roadmap", dto.getTitle());
        assertEquals("Complete Java Learning Path", dto.getDescription());
        assertNotNull(dto.getTasks());
        assertEquals(1, dto.getTasks().size());
    }

    @Test
    void testBuilder_DefaultTasks() {
        // Act
        RoadMapAssignmentRequestDTO dto = RoadMapAssignmentRequestDTO.builder()
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .build();

        // Assert
        assertNotNull(dto);
        assertNotNull(dto.getTasks());
        assertTrue(dto.getTasks().isEmpty());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        RoadMapAssignmentRequestDTO dto = new RoadMapAssignmentRequestDTO();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getTitle());
        assertNull(dto.getDescription());
        // Tasks can be null or empty list depending on constructor used
        assertTrue(dto.getTasks() == null || dto.getTasks().isEmpty());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        List<TaskRequestDTO> tasks = new ArrayList<>();

        // Act
        RoadMapAssignmentRequestDTO dto = new RoadMapAssignmentRequestDTO(
                "Java Roadmap",
                "Complete Java Learning Path",
                tasks
        );

        // Assert
        assertEquals("Java Roadmap", dto.getTitle());
        assertEquals("Complete Java Learning Path", dto.getDescription());
        assertNotNull(dto.getTasks());
        assertTrue(dto.getTasks().isEmpty());
    }

    @Test
    void testSetters() {
        // Arrange
        RoadMapAssignmentRequestDTO dto = new RoadMapAssignmentRequestDTO();
        List<TaskRequestDTO> tasks = new ArrayList<>();

        // Act
        dto.setTitle("Python Roadmap");
        dto.setDescription("Complete Python Learning Path");
        dto.setTasks(tasks);

        // Assert
        assertEquals("Python Roadmap", dto.getTitle());
        assertEquals("Complete Python Learning Path", dto.getDescription());
        assertNotNull(dto.getTasks());
    }

    @Test
    void testGetters() {
        // Arrange
        List<TaskRequestDTO> tasks = new ArrayList<>();
        RoadMapAssignmentRequestDTO dto = new RoadMapAssignmentRequestDTO(
                "JavaScript Roadmap",
                "Complete JS Learning Path",
                tasks
        );

        // Act & Assert
        assertEquals("JavaScript Roadmap", dto.getTitle());
        assertEquals("Complete JS Learning Path", dto.getDescription());
        assertNotNull(dto.getTasks());
    }
}
