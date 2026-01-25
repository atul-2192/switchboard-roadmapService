package com.Switchboard.RoadmapService.Entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoadMapAssignmentTest {

    @Test
    void testBuilder_AllFields() {
        // Arrange
        UUID id = UUID.randomUUID();
        List<Task> tasks = new ArrayList<>();

        // Act
        RoadMapAssignment assignment = RoadMapAssignment.builder()
                .id(id)
                .title("Java Roadmap")
                .description("Complete Java Learning Path")
                .tasks(tasks)
                .build();

        // Assert
        assertNotNull(assignment);
        assertEquals(id, assignment.getId());
        assertEquals("Java Roadmap", assignment.getTitle());
        assertEquals("Complete Java Learning Path", assignment.getDescription());
        assertNotNull(assignment.getTasks());
        assertTrue(assignment.getTasks().isEmpty());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        RoadMapAssignment assignment = new RoadMapAssignment();

        // Assert
        assertNotNull(assignment);
        assertNull(assignment.getId());
        assertNull(assignment.getTitle());
        assertNull(assignment.getDescription());
        // Tasks can be null or empty list depending on constructor used
        assertTrue(assignment.getTasks() == null || assignment.getTasks().isEmpty());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        UUID id = UUID.randomUUID();
        List<Task> tasks = new ArrayList<>();

        // Act
        RoadMapAssignment assignment = new RoadMapAssignment(
                id,
                "Python Roadmap",
                "Complete Python Learning Path",
                tasks
        );

        // Assert
        assertEquals(id, assignment.getId());
        assertEquals("Python Roadmap", assignment.getTitle());
        assertEquals("Complete Python Learning Path", assignment.getDescription());
        assertNotNull(assignment.getTasks());
    }

    @Test
    void testSetters() {
        // Arrange
        RoadMapAssignment assignment = new RoadMapAssignment();
        UUID id = UUID.randomUUID();
        List<Task> tasks = new ArrayList<>();

        // Act
        assignment.setId(id);
        assignment.setTitle("JavaScript Roadmap");
        assignment.setDescription("Complete JS Learning Path");
        assignment.setTasks(tasks);

        // Assert
        assertEquals(id, assignment.getId());
        assertEquals("JavaScript Roadmap", assignment.getTitle());
        assertEquals("Complete JS Learning Path", assignment.getDescription());
        assertNotNull(assignment.getTasks());
    }

    @Test
    void testGetters() {
        // Arrange
        UUID id = UUID.randomUUID();
        List<Task> tasks = new ArrayList<>();
        RoadMapAssignment assignment = new RoadMapAssignment(
                id,
                "C++ Roadmap",
                "Complete C++ Learning Path",
                tasks
        );

        // Act & Assert
        assertEquals(id, assignment.getId());
        assertEquals("C++ Roadmap", assignment.getTitle());
        assertEquals("Complete C++ Learning Path", assignment.getDescription());
        assertNotNull(assignment.getTasks());
    }

    @Test
    void testAddTask() {
        // Arrange
        RoadMapAssignment assignment = RoadMapAssignment.builder()
                .title("Java Roadmap")
                .tasks(new ArrayList<>())
                .build();
        
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .title("Learn Basics")
                .build();

        // Act
        assignment.addTask(task);

        // Assert
        assertEquals(1, assignment.getTasks().size());
        assertEquals("Learn Basics", assignment.getTasks().get(0).getTitle());
    }

    @Test
    void testAddTask_Multiple() {
        // Arrange
        RoadMapAssignment assignment = RoadMapAssignment.builder()
                .title("Java Roadmap")
                .tasks(new ArrayList<>())
                .build();
        
        Task task1 = Task.builder().title("Task 1").build();
        Task task2 = Task.builder().title("Task 2").build();
        Task task3 = Task.builder().title("Task 3").build();

        // Act
        assignment.addTask(task1);
        assignment.addTask(task2);
        assignment.addTask(task3);

        // Assert
        assertEquals(3, assignment.getTasks().size());
        assertEquals("Task 1", assignment.getTasks().get(0).getTitle());
        assertEquals("Task 2", assignment.getTasks().get(1).getTitle());
        assertEquals("Task 3", assignment.getTasks().get(2).getTitle());
    }

    @Test
    void testBuilder_WithoutTasks() {
        // Act
        RoadMapAssignment assignment = RoadMapAssignment.builder()
                .title("Spring Boot Roadmap")
                .description("Master Spring Boot")
                .build();

        // Assert
        assertNotNull(assignment);
        assertEquals("Spring Boot Roadmap", assignment.getTitle());
        assertEquals("Master Spring Boot", assignment.getDescription());
    }
}
