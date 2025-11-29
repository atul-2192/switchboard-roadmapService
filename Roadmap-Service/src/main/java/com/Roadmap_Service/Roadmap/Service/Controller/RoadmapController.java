package com.Roadmap_Service.Roadmap.Service.Controller;

import com.Roadmap_Service.Roadmap.Service.DTO.AddAssignmentToWorkspaceRequestDTO;
import com.Roadmap_Service.Roadmap.Service.DTO.RoadMapAssignmentRequestDTO;
import com.Roadmap_Service.Roadmap.Service.DTO.RoadMapAssignmentResponseDTO;
import com.Roadmap_Service.Roadmap.Service.Service.RoadMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.Roadmap_Service.Roadmap.Service.DTO.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/Roadmap")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@Tag(name = "Roadmap Management", description = "APIs for managing roadmap assignments")
public class RoadmapController {

    private final RoadMapService roadMapService;

    public RoadmapController(RoadMapService roadMapService) {
        this.roadMapService = roadMapService;
        log.info("RoadmapController :: Constructor :: Initialized :: RoadMapService");
    }

    @PostMapping
    @Operation(summary = "Create a new roadmap assignment", description = "Creates a new roadmap assignment with tasks")
    public ResponseEntity<RoadMapAssignmentResponseDTO> createAssignment(@Valid @RequestBody RoadMapAssignmentRequestDTO requestDTO) {
        log.info("RoadmapController :: createAssignment() :: Received Request :: Assignment Title: {}", requestDTO.getTitle());
        RoadMapAssignmentResponseDTO responseDTO = roadMapService.saveAssignment(requestDTO);
        log.info("RoadmapController :: createAssignment() :: Created Successfully :: Assignment ID: {}", responseDTO.getId());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    @Operation(summary = "Get all roadmap assignments", description = "Retrieves a list of all roadmap assignments")
    public List<RoadMapAssignmentResponseDTO> getAllAssignments() {
        log.info("RoadmapController :: getAllAssignments() :: Fetching All :: Assignments");
        List<RoadMapAssignmentResponseDTO> assignments = roadMapService.getAllAssignments();
        log.info("RoadmapController :: getAllAssignments() :: Retrieved Successfully :: Count: {}", assignments.size());
        return assignments;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get assignment by ID", description = "Retrieves a specific roadmap assignment by its ID")
    public ResponseEntity<RoadMapAssignmentResponseDTO> getAssignmentById(
            @Parameter(description = "ID of the assignment to retrieve") @PathVariable UUID id) {
        log.info("RoadmapController :: getAssignmentById() :: Fetching :: Assignment ID: {}", id);
        ResponseEntity<RoadMapAssignmentResponseDTO> response = roadMapService.getAssignmentById(id)
                .map(assignment -> {
                    log.info("RoadmapController :: getAssignmentById() :: Found :: Assignment ID: {}", id);
                    return ResponseEntity.ok(assignment);
                })
                .orElseGet(() -> {
                    log.warn("RoadmapController :: getAssignmentById() :: Not Found :: Assignment ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
        return response;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an assignment", description = "Deletes a roadmap assignment by its ID")
    public ResponseEntity<Void> deleteAssignment(
            @Parameter(description = "ID of the assignment to delete") @PathVariable UUID id) {
        log.info("RoadmapController :: deleteAssignment() :: Deleting :: Assignment ID: {}", id);
        roadMapService.deleteAssignment(id);
        log.info("RoadmapController :: deleteAssignment() :: Deleted Successfully :: Assignment ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-assignment/{id}")
    @Operation(
            summary = "Add a roadmap to workspace and new assignment",
            description = "Adds a roadmap assignment to the specified workspace"
    )
    public ResponseEntity<String> addRoadmapAssignmentToWorkspace(@RequestBody AddAssignmentToWorkspaceRequestDTO request, @RequestHeader("X-User-Id") String userIdHeader) {

        UUID userId = UUID.fromString(userIdHeader);
        log.info("RoadmapController :: addRoadmapAssignmentToWorkspace() :: Received Request :: Assignment ID: {} for user {}", request ,userId);
        ApiResponse apiResponse = roadMapService.addAssignmentRoadmap(request , userId);
        log.info("RoadmapController :: addRoadmapAssignmentToWorkspace() :: Processed Successfully :: Assignment ID: {}", request);
        return ResponseEntity.ok("Roadmap assignment added to workspace successfully.");
    }
}
