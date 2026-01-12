package com.Roadmap_Service.Roadmap.Service.Repository;

import com.Roadmap_Service.Roadmap.Service.Entity.RoadMapAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoadMapRepository extends JpaRepository<RoadMapAssignment, UUID> {
}
