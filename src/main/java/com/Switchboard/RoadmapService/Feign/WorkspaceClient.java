package com.Switchboard.RoadmapService.Feign;

import com.Switchboard.RoadmapService.DTO.ApiResponse;
import com.Switchboard.RoadmapService.DTO.RoadMapAssignmentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "WORKSPACESERVICE")
public interface WorkspaceClient {

    @PostMapping("/api/roadmap/add-assignment")
    ApiResponse addRoadmapAssignmentToWorkspace(RoadMapAssignmentResponseDTO roadMapAssignmentResponseDTO, @RequestHeader("X-User-Id") UUID userID);
}
