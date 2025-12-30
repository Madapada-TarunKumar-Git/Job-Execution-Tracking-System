package com.myapps.dto;

public record JobExecutionResponseDTO(
        Long executionId,
        Long jobId,
        String status,
        String startTime,
        String endTime,
        String durationMs,
        String errorMessage
        ) {}
