package com.myapps.entity;

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

    private String status; // e.g., SUCCESS, FAILURE, RUNNING, STARTED

    @Column(length = 2000)
    private String errorMessage; // Error message in case of failure
    private Long durationMs; // Duration in milliseconds

}
