package com.Roadmap_Service.Roadmap.Service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request DTO for creating or updating a task")
public class TaskRequestDTO {

    @NotBlank(message = "Title is required")
    @Schema(description = "Task title", example = "Complete Module 1")
    private String title;

    @Schema(description = "Task description", example = "Complete all lessons in Module 1")
    private String description;

    @Min(value = 0, message = "Reward points must be positive")
    @Schema(description = "Reward points for completing the task", example = "100")
    private int rewardPoints;

    @Schema(description = "Color for the task title", example = "#FF5733")
    private String titleColor;

    @Min(value = 1, message = "Days to complete must be at least 1")
    @Schema(description = "Number of days to complete the task", example = "7")
    private int daysToComplete;
}
