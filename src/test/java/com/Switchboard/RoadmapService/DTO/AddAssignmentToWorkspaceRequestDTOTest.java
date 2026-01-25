package com.Switchboard.RoadmapService.DTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AddAssignmentToWorkspaceRequestDTOTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        AddAssignmentToWorkspaceRequestDTO dto = new AddAssignmentToWorkspaceRequestDTO();
        UUID assignmentId = UUID.randomUUID();
        UUID taskId1 = UUID.randomUUID();
        UUID taskId2 = UUID.randomUUID();
        List<UUID> taskIds = new ArrayList<>();
        taskIds.add(taskId1);
        taskIds.add(taskId2);

        // Act
        dto.setAssignmentId(assignmentId);
        dto.setTaskIds(taskIds);

        // Assert
        assertEquals(assignmentId, dto.getAssignmentId());
        assertEquals(2, dto.getTaskIds().size());
        assertTrue(dto.getTaskIds().contains(taskId1));
        assertTrue(dto.getTaskIds().contains(taskId2));
    }

    @Test
    void testDefaultConstructor() {
        // Act
        AddAssignmentToWorkspaceRequestDTO dto = new AddAssignmentToWorkspaceRequestDTO();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getAssignmentId());
        assertNull(dto.getTaskIds());
    }

    @Test
    void testSetAssignmentId() {
        // Arrange
        AddAssignmentToWorkspaceRequestDTO dto = new AddAssignmentToWorkspaceRequestDTO();
        UUID assignmentId = UUID.randomUUID();

        // Act
        dto.setAssignmentId(assignmentId);

        // Assert
        assertEquals(assignmentId, dto.getAssignmentId());
    }

    @Test
    void testSetTaskIds_EmptyList() {
        // Arrange
        AddAssignmentToWorkspaceRequestDTO dto = new AddAssignmentToWorkspaceRequestDTO();
        List<UUID> emptyList = new ArrayList<>();

        // Act
        dto.setTaskIds(emptyList);

        // Assert
        assertNotNull(dto.getTaskIds());
        assertTrue(dto.getTaskIds().isEmpty());
    }

    @Test
    void testSetTaskIds_MultipleIds() {
        // Arrange
        AddAssignmentToWorkspaceRequestDTO dto = new AddAssignmentToWorkspaceRequestDTO();
        List<UUID> taskIds = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            taskIds.add(UUID.randomUUID());
        }

        // Act
        dto.setTaskIds(taskIds);

        // Assert
        assertEquals(5, dto.getTaskIds().size());
    }
}
