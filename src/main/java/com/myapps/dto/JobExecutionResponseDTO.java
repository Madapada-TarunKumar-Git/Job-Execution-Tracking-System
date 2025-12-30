package com.myapps.dto;

import com.myapps.domain.ExecutionStatus;

import java.time.LocalDateTime;

public record JobExecutionResponseDTO(
        Long executionId,
        Long jobId,
        ExecutionStatus status,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long durationMs,
        String errorMessage
        ) {}
