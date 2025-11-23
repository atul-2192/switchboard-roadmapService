package com.Roadmap_Service.Roadmap.Service.Mapper;

import com.Roadmap_Service.Roadmap.Service.DTO.RoadMapAssignmentRequestDTO;
import com.Roadmap_Service.Roadmap.Service.DTO.RoadMapAssignmentResponseDTO;
import com.Roadmap_Service.Roadmap.Service.DTO.TaskRequestDTO;
import com.Roadmap_Service.Roadmap.Service.DTO.TaskResponseDTO;
import com.Roadmap_Service.Roadmap.Service.Entity.RoadMapAssignment;
import com.Roadmap_Service.Roadmap.Service.Entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RoadMapMapper {

    // Task Entity to Response DTO
    public TaskResponseDTO toTaskResponseDTO(Task task) {
        log.debug("RoadMapMapper :: toTaskResponseDTO() :: Converting :: Task Entity to Response DTO");
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .rewardPoints(task.getRewardPoints())
                .titleColor(task.getTitleColor())
                .daysToComplete(task.getDaysToComplete())
                .build();
    }

    // Task Request DTO to Entity
    public Task toTaskEntity(TaskRequestDTO dto) {
        log.debug("RoadMapMapper :: toTaskEntity() :: Converting :: Task Request DTO to Entity");
        return Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .rewardPoints(dto.getRewardPoints())
                .titleColor(dto.getTitleColor())
                .daysToComplete(dto.getDaysToComplete())
                .build();
    }

    // Update Task Entity from Request DTO
    public void updateTaskFromDTO(Task task, TaskRequestDTO dto) {
        log.debug("RoadMapMapper :: updateTaskFromDTO() :: Updating :: Task Entity from Request DTO");
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setRewardPoints(dto.getRewardPoints());
        task.setTitleColor(dto.getTitleColor());
        task.setDaysToComplete(dto.getDaysToComplete());
    }

    // RoadMapAssignment Entity to Response DTO
    public RoadMapAssignmentResponseDTO toRoadMapAssignmentResponseDTO(RoadMapAssignment assignment) {
        log.debug("RoadMapMapper :: toRoadMapAssignmentResponseDTO() :: Converting :: Assignment Entity to Response DTO");
        
        List<TaskResponseDTO> taskDTOs = assignment.getTasks() != null
                ? assignment.getTasks().stream()
                        .map(this::toTaskResponseDTO)
                        .collect(Collectors.toList())
                : new ArrayList<>();

        return RoadMapAssignmentResponseDTO.builder()
                .id(assignment.getId())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .tasks(taskDTOs)
                .build();
    }

    // RoadMapAssignment Request DTO to Entity
    public RoadMapAssignment toRoadMapAssignmentEntity(RoadMapAssignmentRequestDTO dto) {
        log.debug("RoadMapMapper :: toRoadMapAssignmentEntity() :: Converting :: Assignment Request DTO to Entity");
        
        RoadMapAssignment assignment = RoadMapAssignment.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .tasks(new ArrayList<>())
                .build();

        if (dto.getTasks() != null) {
            for (TaskRequestDTO taskDTO : dto.getTasks()) {
                Task task = toTaskEntity(taskDTO);
                assignment.getTasks().add(task);
            }
        }

        return assignment;
    }

    // Update RoadMapAssignment Entity from Request DTO
    public void updateRoadMapAssignmentFromDTO(RoadMapAssignment assignment, RoadMapAssignmentRequestDTO dto) {
        log.debug("RoadMapMapper :: updateRoadMapAssignmentFromDTO() :: Updating :: Assignment Entity from Request DTO");
        assignment.setTitle(dto.getTitle());
        assignment.setDescription(dto.getDescription());
    }

    // Convert List of Assignments to Response DTOs
    public List<RoadMapAssignmentResponseDTO> toRoadMapAssignmentResponseDTOList(List<RoadMapAssignment> assignments) {
        log.debug("RoadMapMapper :: toRoadMapAssignmentResponseDTOList() :: Converting :: List of Assignment Entities to Response DTOs");
        return assignments.stream()
                .map(this::toRoadMapAssignmentResponseDTO)
                .collect(Collectors.toList());
    }

    // Convert List of Tasks to Response DTOs
    public List<TaskResponseDTO> toTaskResponseDTOList(List<Task> tasks) {
        log.debug("RoadMapMapper :: toTaskResponseDTOList() :: Converting :: List of Task Entities to Response DTOs");
        return tasks.stream()
                .map(this::toTaskResponseDTO)
                .collect(Collectors.toList());
    }
}
