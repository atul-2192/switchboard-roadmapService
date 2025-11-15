package com.Roadmap_Service.Roadmap.Service.Controller;

import com.Roadmap_Service.Roadmap.Service.DTO.TaskRequestDTO;
import com.Roadmap_Service.Roadmap.Service.DTO.TaskResponseDTO;
import com.Roadmap_Service.Roadmap.Service.Service.TaskService;
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
@RequestMapping("/api/tasks")
@Slf4j
@Tag(name = "Task Management", description = "APIs for managing tasks within roadmap assignments")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        log.info("TaskController :: Constructor :: Initialized :: TaskService");
    }

    // Add task under specific assignment
    @PostMapping("/assignment/{assignmentId}")
    @Operation(summary = "Add task to assignment", description = "Creates a new task and assigns it to a specific roadmap assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Assignment not found", content = @Content)
    })
    public ResponseEntity<TaskResponseDTO> addTask(
            @Parameter(description = "ID of the assignment to add task to") @PathVariable UUID assignmentId,
            @Valid @RequestBody TaskRequestDTO requestDTO) {
        log.info("TaskController :: addTask() :: Received Request :: Assignment ID: {}, Task Title: {}", assignmentId, requestDTO.getTitle());
        TaskResponseDTO responseDTO = taskService.addTaskToAssignment(assignmentId, requestDTO);
        log.info("TaskController :: addTask() :: Created Successfully :: Task ID: {}", responseDTO.getId());
        return ResponseEntity.ok(responseDTO);
    }

    // Update task by taskId
    @PutMapping("/{taskId}")
    @Operation(summary = "Update task", description = "Updates an existing task with new details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    public ResponseEntity<TaskResponseDTO> updateTask(
            @Parameter(description = "ID of the task to update") @PathVariable UUID taskId,
            @Valid @RequestBody TaskRequestDTO requestDTO) {
        log.info("TaskController :: updateTask() :: Received Request :: Task ID: {}", taskId);
        TaskResponseDTO responseDTO = taskService.updateTask(taskId, requestDTO);
        log.info("TaskController :: updateTask() :: Updated Successfully :: Task ID: {}", responseDTO.getId());
        return ResponseEntity.ok(responseDTO);
    }

    // Delete task
    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete task", description = "Deletes a task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID of the task to delete") @PathVariable UUID taskId) {
        log.info("TaskController :: deleteTask() :: Deleting :: Task ID: {}", taskId);
        taskService.deleteTask(taskId);
        log.info("TaskController :: deleteTask() :: Deleted Successfully :: Task ID: {}", taskId);
        return ResponseEntity.noContent().build();
    }

    // Get all tasks of a specific assignment
    @GetMapping("/assignment/{assignmentId}")
    @Operation(summary = "Get tasks by assignment", description = "Retrieves all tasks for a specific roadmap assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tasks"),
            @ApiResponse(responseCode = "404", description = "Assignment not found", content = @Content)
    })
    public ResponseEntity<List<TaskResponseDTO>> getTasksByAssignment(
            @Parameter(description = "ID of the assignment to get tasks for") @PathVariable UUID assignmentId) {
        log.info("TaskController :: getTasksByAssignment() :: Fetching :: Assignment ID: {}", assignmentId);
        List<TaskResponseDTO> tasks = taskService.getTasksByAssignment(assignmentId);
        log.info("TaskController :: getTasksByAssignment() :: Retrieved Successfully :: Count: {}", tasks.size());
        return ResponseEntity.ok(tasks);
    }
}
