package com.myapps.dto;

import jakarta.validation.constraints.NotNull;

@NotNull
public record JobRequestDTO(
        String jobName,
        String ownerTeam,
        String environment
        ) { }
