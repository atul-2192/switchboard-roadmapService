package com.Switchboard.RoadmapService.Entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testBuilder_AllFields() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID assignmentId = UUID.randomUUID();

        // Act
        Task task = Task.builder()
                .id(id)
                .topic("Java Fundamentals")
                .orderNumber(1)
                .title("Learn Java Basics")
                .description("Complete Java fundamentals")
                .rewardPoints(100)
                .titleColor("#FF5733")
                .assignmentId(assignmentId)
                .estimatedHours(40.0)
                .build();

        // Assert
        assertNotNull(task);
        assertEquals(id, task.getId());
        assertEquals("Java Fundamentals", task.getTopic());
        assertEquals(1, task.getOrderNumber());
        assertEquals("Learn Java Basics", task.getTitle());
        assertEquals("Complete Java fundamentals", task.getDescription());
        assertEquals(100, task.getRewardPoints());
        assertEquals("#FF5733", task.getTitleColor());
        assertEquals(assignmentId, task.getAssignmentId());
        assertEquals(40.0, task.getEstimatedHours());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Task task = new Task();

        // Assert
        assertNotNull(task);
        assertNull(task.getId());
        assertNull(task.getTopic());
        assertEquals(0, task.getOrderNumber());
        assertNull(task.getTitle());
        assertNull(task.getDescription());
        assertEquals(0, task.getRewardPoints());
        assertNull(task.getTitleColor());
        assertNull(task.getAssignmentId());
        assertNull(task.getEstimatedHours());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID assignmentId = UUID.randomUUID();

        // Act
        Task task = new Task(
                id,
                "Advanced Java",
                2,
                "Learn Advanced Concepts",
                "Master advanced Java topics",
                200,
                "#0000FF",
                assignmentId,
                60.0
        );

        // Assert
        assertEquals(id, task.getId());
        assertEquals("Advanced Java", task.getTopic());
        assertEquals(2, task.getOrderNumber());
        assertEquals("Learn Advanced Concepts", task.getTitle());
        assertEquals("Master advanced Java topics", task.getDescription());
        assertEquals(200, task.getRewardPoints());
        assertEquals("#0000FF", task.getTitleColor());
        assertEquals(assignmentId, task.getAssignmentId());
        assertEquals(60.0, task.getEstimatedHours());
    }

    @Test
    void testSetters() {
        // Arrange
        Task task = new Task();
        UUID id = UUID.randomUUID();
        UUID assignmentId = UUID.randomUUID();

        // Act
        task.setId(id);
        task.setTopic("Spring Framework");
        task.setOrderNumber(3);
        task.setTitle("Master Spring");
        task.setDescription("Learn Spring Framework");
        task.setRewardPoints(150);
        task.setTitleColor("#00FF00");
        task.setAssignmentId(assignmentId);
        task.setEstimatedHours(50.0);

        // Assert
        assertEquals(id, task.getId());
        assertEquals("Spring Framework", task.getTopic());
        assertEquals(3, task.getOrderNumber());
        assertEquals("Master Spring", task.getTitle());
        assertEquals("Learn Spring Framework", task.getDescription());
        assertEquals(150, task.getRewardPoints());
        assertEquals("#00FF00", task.getTitleColor());
        assertEquals(assignmentId, task.getAssignmentId());
        assertEquals(50.0, task.getEstimatedHours());
    }

    @Test
    void testGetters() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID assignmentId = UUID.randomUUID();
        Task task = new Task(
                id,
                "Hibernate ORM",
                4,
                "Database Operations",
                "Master Hibernate",
                120,
                "#FFFF00",
                assignmentId,
                45.0
        );

        // Act & Assert
        assertEquals(id, task.getId());
        assertEquals("Hibernate ORM", task.getTopic());
        assertEquals(4, task.getOrderNumber());
        assertEquals("Database Operations", task.getTitle());
        assertEquals("Master Hibernate", task.getDescription());
        assertEquals(120, task.getRewardPoints());
        assertEquals("#FFFF00", task.getTitleColor());
        assertEquals(assignmentId, task.getAssignmentId());
        assertEquals(45.0, task.getEstimatedHours());
    }

    @Test
    void testBuilder_PartialFields() {
        // Act
        Task task = Task.builder()
                .title("Microservices")
                .rewardPoints(300)
                .build();

        // Assert
        assertNotNull(task);
        assertEquals("Microservices", task.getTitle());
        assertEquals(300, task.getRewardPoints());
        assertNull(task.getTopic());
        assertEquals(0, task.getOrderNumber());
        assertNull(task.getDescription());
    }

    @Test
    void testBuilder_MinimalFields() {
        // Act
        Task task = Task.builder()
                .title("REST APIs")
                .build();

        // Assert
        assertNotNull(task);
        assertEquals("REST APIs", task.getTitle());
        assertEquals(0, task.getRewardPoints());
        assertEquals(0, task.getOrderNumber());
    }
}
