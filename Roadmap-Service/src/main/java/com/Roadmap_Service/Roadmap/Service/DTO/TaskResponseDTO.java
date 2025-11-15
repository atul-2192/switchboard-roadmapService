package com.Roadmap_Service.Roadmap.Service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response DTO for task information")
public class TaskResponseDTO {

    @Schema(description = "Task ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Task title", example = "Complete Module 1")
    private String title;

    @Schema(description = "Task description", example = "Complete all lessons in Module 1")
    private String description;

    @Schema(description = "Reward points for completing the task", example = "100")
    private int rewardPoints;

    @Schema(description = "Color for the task title", example = "#FF5733")
    private String titleColor;

    @Schema(description = "Number of days to complete the task", example = "7")
    private int daysToComplete;

    @Schema(description = "Assignment ID this task belongs to", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID assignmentId;
}
