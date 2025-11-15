package com.Roadmap_Service.Roadmap.Service.Service;

import com.Roadmap_Service.Roadmap.Service.DTO.TaskRequestDTO;
import com.Roadmap_Service.Roadmap.Service.DTO.TaskResponseDTO;
import com.Roadmap_Service.Roadmap.Service.Entity.RoadMapAssignment;
import com.Roadmap_Service.Roadmap.Service.Entity.Task;
import com.Roadmap_Service.Roadmap.Service.Mapper.RoadMapMapper;
import com.Roadmap_Service.Roadmap.Service.Repository.RoadMapRepository;
import com.Roadmap_Service.Roadmap.Service.Repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final RoadMapRepository roadMapRepository;
    private final RoadMapMapper mapper;

    public TaskService(TaskRepository taskRepository, RoadMapRepository roadMapRepository, RoadMapMapper mapper) {
        this.taskRepository = taskRepository;
        this.roadMapRepository = roadMapRepository;
        this.mapper = mapper;
        log.info("TaskService :: Constructor :: Initialized :: TaskRepository, RoadMapRepository and RoadMapMapper");
    }

    @SuppressWarnings("null")
    public TaskResponseDTO addTaskToAssignment(UUID assignmentId, TaskRequestDTO requestDTO) {
        log.info("TaskService :: addTaskToAssignment() :: Adding Task :: Assignment ID: {}, Task Title: {}", 
                assignmentId, requestDTO.getTitle());

        RoadMapAssignment assignment = roadMapRepository.findById(assignmentId)
                .orElseThrow(() -> {
                    log.error("TaskService :: addTaskToAssignment() :: Assignment Not Found :: Assignment ID: {}", assignmentId);
                    return new RuntimeException("Assignment not found with id: " + assignmentId);
                });

        Task task = mapper.toTaskEntity(requestDTO);
        task.setAssignment(assignment);
        Task saved = taskRepository.save(task);

        log.info("TaskService :: addTaskToAssignment() :: Task Added Successfully :: Task ID: {}, Assignment ID: {}", 
                saved.getId(), assignmentId);
        return mapper.toTaskResponseDTO(saved);
    }

    @SuppressWarnings("null")
    public TaskResponseDTO updateTask(UUID taskId, TaskRequestDTO requestDTO) {
        log.info("TaskService :: updateTask() :: Updating :: Task ID: {}", taskId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    log.error("TaskService :: updateTask() :: Task Not Found :: Task ID: {}", taskId);
                    return new RuntimeException("Task not found with id: " + taskId);
                });

        log.info("TaskService :: updateTask() :: Setting Properties :: Task ID: {}", taskId);
        mapper.updateTaskFromDTO(task, requestDTO);
        Task updated = taskRepository.save(task);

        log.info("TaskService :: updateTask() :: Updated Successfully :: Task ID: {}", updated.getId());
        return mapper.toTaskResponseDTO(updated);
    }

    @SuppressWarnings("null")
    public void deleteTask(UUID taskId) {
        log.info("TaskService :: deleteTask() :: Deleting :: Task ID: {}", taskId);

        if (!taskRepository.existsById(taskId)) {
            log.error("TaskService :: deleteTask() :: Task Not Found :: Task ID: {}", taskId);
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        taskRepository.deleteById(taskId);
        log.info("TaskService :: deleteTask() :: Deleted Successfully :: Task ID: {}", taskId);
    }

    @SuppressWarnings("null")
    public List<TaskResponseDTO> getTasksByAssignment(UUID assignmentId) {
        log.info("TaskService :: getTasksByAssignment() :: Fetching :: Assignment ID: {}", assignmentId);

        RoadMapAssignment assignment = roadMapRepository.findById(assignmentId)
                .orElseThrow(() -> {
                    log.error("TaskService :: getTasksByAssignment() :: Assignment Not Found :: Assignment ID: {}", assignmentId);
                    return new RuntimeException("Assignment not found with id: " + assignmentId);
                });

        List<Task> tasks = assignment.getTasks();
        log.info("TaskService :: getTasksByAssignment() :: Fetched Successfully :: Count: {}, Assignment ID: {}", 
                tasks.size(), assignmentId);
        return mapper.toTaskResponseDTOList(tasks);
    }
}
