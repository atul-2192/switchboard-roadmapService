package com.Roadmap_Service.Roadmap.Service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response DTO for roadmap assignment information")
public class RoadMapAssignmentResponseDTO {

    @Schema(description = "Assignment ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Assignment title", example = "Java Development Roadmap")
    private String title;

    @Schema(description = "Assignment description", example = "Complete roadmap for becoming a Java developer")
    private String description;

    @Schema(description = "List of tasks in the assignment")
    @Builder.Default
    private List<TaskResponseDTO> tasks = new ArrayList<>();
}
