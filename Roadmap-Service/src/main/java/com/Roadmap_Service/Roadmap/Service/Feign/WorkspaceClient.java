package com.Roadmap_Service.Roadmap.Service.Feign;

import com.Roadmap_Service.Roadmap.Service.DTO.ApiResponse;
import com.Roadmap_Service.Roadmap.Service.DTO.RoadMapAssignmentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "WORKSPACESERVICE")
public interface WorkspaceClient {

    @PostMapping("/api/roadmap/add-assignment")
    ApiResponse addRoadmapAssignmentToWorkspace(RoadMapAssignmentResponseDTO roadMapAssignmentResponseDTO, @RequestHeader("X-User-Id") UUID userID);
}
