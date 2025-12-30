package com.myapps.dto;

public record JobRequestDTO(
        String jobName,
        String ownerTeam,
        String environment
        ) { }
