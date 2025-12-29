package com.myapps.entity;

import com.myapps.domain.ExecutionStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class JobExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long executionId;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus status; // e.g., STARTED, SUCCESS, FAILURE

    @Column(length = 2000)
    private String errorMessage; // Error message in case of failure
    private Long durationMs; // Duration in milliseconds

}
