package com.Roadmap_Service.Roadmap.Service.Service;

import com.Roadmap_Service.Roadmap.Service.DTO.*;
import com.Roadmap_Service.Roadmap.Service.Entity.RoadMapAssignment;
import com.Roadmap_Service.Roadmap.Service.Entity.Task;
import com.Roadmap_Service.Roadmap.Service.Feign.WorkspaceClient;
import com.Roadmap_Service.Roadmap.Service.Mapper.RoadMapMapper;
import com.Roadmap_Service.Roadmap.Service.Repository.RoadMapRepository;
import com.Roadmap_Service.Roadmap.Service.Repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoadMapService {

    private final RoadMapRepository roadMapRepository;
    private final RoadMapMapper mapper;
    private final WorkspaceClient workspaceClient;
    private final TaskRepository taskRepository;

    @SuppressWarnings("null")
    public RoadMapAssignmentResponseDTO saveAssignment(RoadMapAssignmentRequestDTO requestDTO) {
        log.info("RoadMapService :: saveAssignment() :: Saving :: Assignment Title: {}", requestDTO.getTitle());

        try {
            RoadMapAssignment assignment = mapper.toRoadMapAssignmentEntity(requestDTO);
            RoadMapAssignment saved = roadMapRepository.save(assignment);
            log.info("RoadMapService :: saveAssignment() :: Saved Successfully :: Assignment ID: {}", saved.getId());
            return mapper.toRoadMapAssignmentResponseDTO(saved);
        } catch (Exception e) {
            log.error("RoadMapService :: saveAssignment() :: Error Occurred :: Assignment Title: {}", requestDTO.getTitle(), e);
            throw e;
        }
    }

    public List<RoadMapAssignmentResponseDTO> getAllAssignments() {
        log.info("RoadMapService :: getAllAssignments() :: Fetching All :: Assignments");
        try {
            List<RoadMapAssignment> assignments = roadMapRepository.findAll();
            log.info("RoadMapService :: getAllAssignments() :: Fetched Successfully :: Count: {}", assignments.size());
            return mapper.toRoadMapAssignmentResponseDTOList(assignments);
        } catch (Exception e) {
            log.error("RoadMapService :: getAllAssignments() :: Error Occurred :: Fetching Assignments", e);
            throw e;
        }
    }

    @SuppressWarnings("null")
    public Optional<RoadMapAssignmentResponseDTO> getAssignmentById(UUID id) {
        log.info("RoadMapService :: getAssignmentById() :: Fetching :: Assignment ID: {}", id);
        try {
            Optional<RoadMapAssignment> assignment = roadMapRepository.findById(id);
            if (assignment.isPresent()) {
                log.info("RoadMapService :: getAssignmentById() :: Found :: Assignment ID: {}, Title: {}", 
                        assignment.get().getId(), assignment.get().getTitle());
                return Optional.of(mapper.toRoadMapAssignmentResponseDTO(assignment.get()));
            } else {
                log.warn("RoadMapService :: getAssignmentById() :: Not Found :: Assignment ID: {}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("RoadMapService :: getAssignmentById() :: Error Occurred :: Assignment ID: {}", id, e);
            throw e;
        }
    }

    @SuppressWarnings("null")
    public void deleteAssignment(UUID id) {
        log.info("RoadMapService :: deleteAssignment() :: Deleting :: Assignment ID: {}", id);
        try {
            roadMapRepository.deleteById(id);
            log.info("RoadMapService :: deleteAssignment() :: Deleted Successfully :: Assignment ID: {}", id);
        } catch (Exception e) {
            log.error("RoadMapService :: deleteAssignment() :: Error Occurred :: Assignment ID: {}", id, e);
            throw e;
        }
    }
    public ApiResponse addAssignmentRoadmap(AddAssignmentToWorkspaceRequestDTO request, UUID userId){

        UUID assignmentId = request.getAssignmentId();
        List<UUID> taskIds = request.getTaskIds();

        log.info("RoadMapService :: addSelectedTasksToWorkspace() :: assignmentId: {}, taskIds: {}",
                assignmentId, taskIds);

        // 1. Fetch Assignment
        RoadMapAssignment assignment = roadMapRepository.findById(assignmentId).orElse(null);
        if (assignment == null) {
            log.info("Assignment not found :: {}", assignmentId);
            return ApiResponse.response("Assignment not found", false);
        }

        // 2. Fetch only selected tasks (in ONE SQL query)
        List<Task> tasks = taskRepository.findAllById(taskIds);

        if (tasks.isEmpty()) {
            log.info("No tasks found for provided taskIds :: {}", taskIds);
            return ApiResponse.response("No tasks found for provided taskIds", false);
        }

        // 3. Log if any missing IDs
        if (tasks.size() != taskIds.size()) {
            log.info("Some taskIds not found. Requested: {}, Found: {}",
                    taskIds.size(), tasks.size());
        }

        // 4. Prepare DTO for Workspace Service
        RoadMapAssignmentResponseDTO assignmentDTO = mapper.toRoadMapAssignmentResponseDTO(assignment);

        List<TaskResponseDTO> taskDTOs = mapper.toTaskResponseDTOList(tasks);
        assignmentDTO.setTasks(taskDTOs);

        ApiResponse apiResponse = workspaceClient.addRoadmapAssignmentToWorkspace(assignmentDTO,userId);

        log.info("RoadMapService :: addAssignmentRoadmap() :: Roadmap Assignment Added :: Assignment ID: {}", assignmentId);
        return apiResponse;
    }
}
