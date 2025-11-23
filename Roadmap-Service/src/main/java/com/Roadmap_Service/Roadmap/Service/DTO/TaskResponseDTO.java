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

    private UUID id;
    private String title;
    private String description;
    private int rewardPoints;
    private String titleColor;
    private int daysToComplete;
    private UUID assignmentId;
    private String topic;
    private int orderNumber;
}