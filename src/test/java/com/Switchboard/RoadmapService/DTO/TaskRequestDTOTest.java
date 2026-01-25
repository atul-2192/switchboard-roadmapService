package com.Switchboard.RoadmapService.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskRequestDTOTest {

    @Test
    void testBuilder_AllFields() {
        // Act
        TaskRequestDTO dto = TaskRequestDTO.builder()
                .title("Learn Java Basics")
                .description("Complete Java fundamentals")
                .rewardPoints(100)
                .titleColor("#FF5733")
                .estimatedHours(40.0)
                .build();

        // Assert
        assertNotNull(dto);
        assertEquals("Learn Java Basics", dto.getTitle());
        assertEquals("Complete Java fundamentals", dto.getDescription());
        assertEquals(100, dto.getRewardPoints());
        assertEquals("#FF5733", dto.getTitleColor());
        assertEquals(40.0, dto.getEstimatedHours());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        TaskRequestDTO dto = new TaskRequestDTO();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getTitle());
        assertNull(dto.getDescription());
        assertEquals(0, dto.getRewardPoints());
        assertNull(dto.getTitleColor());
        assertEquals(0.0, dto.getEstimatedHours());
    }

    @Test
    void testAllArgsConstructor() {
        // Act
        TaskRequestDTO dto = new TaskRequestDTO(
                "Advanced Java",
                "Learn advanced concepts",
                200,
                "#0000FF",
                60.0
        );

        // Assert
        assertEquals("Advanced Java", dto.getTitle());
        assertEquals("Learn advanced concepts", dto.getDescription());
        assertEquals(200, dto.getRewardPoints());
        assertEquals("#0000FF", dto.getTitleColor());
        assertEquals(60.0, dto.getEstimatedHours());
    }

    @Test
    void testSetters() {
        // Arrange
        TaskRequestDTO dto = new TaskRequestDTO();

        // Act
        dto.setTitle("Spring Boot");
        dto.setDescription("Master Spring Boot framework");
        dto.setRewardPoints(150);
        dto.setTitleColor("#00FF00");
        dto.setEstimatedHours(50.0);

        // Assert
        assertEquals("Spring Boot", dto.getTitle());
        assertEquals("Master Spring Boot framework", dto.getDescription());
        assertEquals(150, dto.getRewardPoints());
        assertEquals("#00FF00", dto.getTitleColor());
        assertEquals(50.0, dto.getEstimatedHours());
    }

    @Test
    void testGetters() {
        // Arrange
        TaskRequestDTO dto = new TaskRequestDTO(
                "Hibernate ORM",
                "Database operations",
                120,
                "#FFFF00",
                45.0
        );

        // Act & Assert
        assertEquals("Hibernate ORM", dto.getTitle());
        assertEquals("Database operations", dto.getDescription());
        assertEquals(120, dto.getRewardPoints());
        assertEquals("#FFFF00", dto.getTitleColor());
        assertEquals(45.0, dto.getEstimatedHours());
    }

    @Test
    void testBuilder_PartialFields() {
        // Act
        TaskRequestDTO dto = TaskRequestDTO.builder()
                .title("Microservices")
                .rewardPoints(300)
                .build();

        // Assert
        assertNotNull(dto);
        assertEquals("Microservices", dto.getTitle());
        assertNull(dto.getDescription());
        assertEquals(300, dto.getRewardPoints());
        assertNull(dto.getTitleColor());
        assertEquals(0.0, dto.getEstimatedHours());
    }
}
