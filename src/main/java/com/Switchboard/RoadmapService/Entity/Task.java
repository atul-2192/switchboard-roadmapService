package com.Switchboard.RoadmapService.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "roadmap_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String topic;
    private int orderNumber;
    private String title;
    private String description;
    private int rewardPoints;
    private String titleColor;
    @Column(name="assignment_id", nullable = false, updatable = false)
    private UUID assignmentId;
    private Double estimatedHours;

}
