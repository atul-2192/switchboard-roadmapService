package com.Roadmap_Service.Roadmap.Service.Service;

import com.Roadmap_Service.Roadmap.Service.DTO.ApiResponse;
import com.Roadmap_Service.Roadmap.Service.DTO.RoadMapAssignmentRequestDTO;
import com.Roadmap_Service.Roadmap.Service.DTO.RoadMapAssignmentResponseDTO;
import com.Roadmap_Service.Roadmap.Service.DTO.TaskResponseDTO;
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
    public ApiResponse addAssignmentRoadmap(UUID assignmentId, UUID userId){
        log.info("RoadMapService :: addAssignmentRoadmap() :: Adding Roadmap Assignment :: Assignment ID: {}", assignmentId);
        RoadMapAssignment roadMapAssignment = roadMapRepository.findById(assignmentId).orElse(null);
        if(roadMapAssignment == null){
            log.error("RoadMapService :: addAssignmentRoadmap() :: Roadmap Assignment Not Found :: Assignment ID: {}", assignmentId);
            return ApiResponse.response("Roadmap Assignment not found", false);
        }
        List<Task> tasks=taskRepository.findByAssignmentId(assignmentId);
        if(tasks.isEmpty()){
            log.info("RoadMapService :: addAssignmentRoadmap() :: No Tasks Found for Assignment :: Assignment ID: {}", assignmentId);
        }
        new RoadMapAssignmentResponseDTO();
        RoadMapAssignmentResponseDTO roadMapAssignmentResponseDTO=mapper.toRoadMapAssignmentResponseDTO(roadMapAssignment);
        List<TaskResponseDTO> taskResponseDTO=mapper.toTaskResponseDTOList(tasks);
        roadMapAssignmentResponseDTO.setTasks(taskResponseDTO);
        if(roadMapAssignmentResponseDTO.getTasks().isEmpty()){
            log.info("RoadMapService :: addAssignmentRoadmap() :: No Task DTOs to Add for Assignment :: Assignment ID: {}", assignmentId);
        }
        ApiResponse apiResponse = workspaceClient.addRoadmapAssignmentToWorkspace(roadMapAssignmentResponseDTO,userId);

        log.info("RoadMapService :: addAssignmentRoadmap() :: Roadmap Assignment Added :: Assignment ID: {}", assignmentId);
        return apiResponse;
    }
}
