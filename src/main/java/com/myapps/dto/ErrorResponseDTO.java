package com.myapps.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        LocalDateTime timeStamp,
        int status,
        String errorMessage
//        String path
) {}
