package com.Roadmap_Service.Roadmap.Service.Controller;

import com.Roadmap_Service.Roadmap.Service.DTO.RoadMapAssignmentRequestDTO;
import com.Roadmap_Service.Roadmap.Service.DTO.RoadMapAssignmentResponseDTO;
import com.Roadmap_Service.Roadmap.Service.Service.RoadMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment created successfully",
                    content = @Content(schema = @Schema(implementation = RoadMapAssignmentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<RoadMapAssignmentResponseDTO> createAssignment(@Valid @RequestBody RoadMapAssignmentRequestDTO requestDTO) {
        log.info("RoadmapController :: createAssignment() :: Received Request :: Assignment Title: {}", requestDTO.getTitle());
        RoadMapAssignmentResponseDTO responseDTO = roadMapService.saveAssignment(requestDTO);
        log.info("RoadmapController :: createAssignment() :: Created Successfully :: Assignment ID: {}", responseDTO.getId());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    @Operation(summary = "Get all roadmap assignments", description = "Retrieves a list of all roadmap assignments")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of assignments")
    public List<RoadMapAssignmentResponseDTO> getAllAssignments() {
        log.info("RoadmapController :: getAllAssignments() :: Fetching All :: Assignments");
        List<RoadMapAssignmentResponseDTO> assignments = roadMapService.getAllAssignments();
        log.info("RoadmapController :: getAllAssignments() :: Retrieved Successfully :: Count: {}", assignments.size());
        return assignments;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get assignment by ID", description = "Retrieves a specific roadmap assignment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment found",
                    content = @Content(schema = @Schema(implementation = RoadMapAssignmentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Assignment not found", content = @Content)
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assignment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    public ResponseEntity<Void> deleteAssignment(
            @Parameter(description = "ID of the assignment to delete") @PathVariable UUID id) {
        log.info("RoadmapController :: deleteAssignment() :: Deleting :: Assignment ID: {}", id);
        roadMapService.deleteAssignment(id);
        log.info("RoadmapController :: deleteAssignment() :: Deleted Successfully :: Assignment ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
