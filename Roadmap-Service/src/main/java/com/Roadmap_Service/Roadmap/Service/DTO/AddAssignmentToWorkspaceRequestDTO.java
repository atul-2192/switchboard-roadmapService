package com.Roadmap_Service.Roadmap.Service.DTO;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class AddAssignmentToWorkspaceRequestDTO {
    private UUID assignmentId;
    private List<UUID> taskIds;
}