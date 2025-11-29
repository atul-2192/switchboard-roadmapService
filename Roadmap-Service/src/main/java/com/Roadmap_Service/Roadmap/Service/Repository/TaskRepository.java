package com.Roadmap_Service.Roadmap.Service.Repository;

import com.Roadmap_Service.Roadmap.Service.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllById(Iterable<UUID> ids);
}
