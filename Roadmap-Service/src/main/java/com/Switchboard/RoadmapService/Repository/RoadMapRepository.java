package com.Switchboard.RoadmapService.Repository;

import com.Switchboard.RoadmapService.Entity.RoadMapAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoadMapRepository extends JpaRepository<RoadMapAssignment, UUID> {
}
