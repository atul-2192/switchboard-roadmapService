package com.Roadmap_Service.Roadmap.Service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request DTO for creating or updating a roadmap assignment")
public class RoadMapAssignmentRequestDTO {

    @NotBlank(message = "Title is required")
    @Schema(description = "Assignment title", example = "Java Development Roadmap")
    private String title;

    @Schema(description = "Assignment description", example = "Complete roadmap for becoming a Java developer")
    private String description;

    @Schema(description = "List of tasks in the assignment")
    @Builder.Default
    private List<TaskRequestDTO> tasks = new ArrayList<>();
}
