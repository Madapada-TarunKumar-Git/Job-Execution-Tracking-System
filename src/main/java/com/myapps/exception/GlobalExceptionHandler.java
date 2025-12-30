package com.myapps.exception;

import com.myapps.domain.MsgSrc;
import com.myapps.dto.ErrorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MsgSrc msgSrc;

    @ExceptionHandler(ExecutionNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleExecutionNotFoundException(ExecutionNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
                );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
