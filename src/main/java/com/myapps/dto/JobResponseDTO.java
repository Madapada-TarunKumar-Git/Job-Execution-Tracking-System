package com.myapps.dto;

public record JobResponseDTO(
        Long jobId,
        String jobName,
        String jobType,
        String ownerTeam,
        String environment
        ) {}
