package com.myapps.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public record ErrorResponseDTO(
        Instant timeStamp,
        int status,
        String errorMessage
//        String path
) {}
