package com.myapps.controller;

import com.myapps.domain.ExecutionStatus;
import com.myapps.dto.JobExecutionResponseDTO;
import com.myapps.dto.JobExecutionStatusUpdateDTO;
import com.myapps.service.JobExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Controller for managing job executions.
 *
 * Improvements:
 * - Uses typed ExecutionStatus enum in request DTO for safer handling
 * - Produces/consumes JSON explicitly where applicable
 * - Returns 201 with Location for created executions
 * - Consolidates status updates into a single PATCH endpoint
 */
@RestController
@RequestMapping(path = "/api/v1/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Job Execution Management", description = "APIs for managing job executions")
@Slf4j
@Validated
public class JobExecutionController {
    private final JobExecutionService jobExecutionService;

    @Operation(summary = "Start a new execution for a job")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Execution started"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    @PostMapping(path = "/{jobId}/executions")
    public ResponseEntity<JobExecutionResponseDTO> startExecution(@Positive @PathVariable Long jobId) {
        log.info("Request to start execution for jobId={}", jobId);
        JobExecutionResponseDTO responseDTO = jobExecutionService.startExecution(jobId);

        // Location: /api/v1/jobs/{jobId}/executions/{executionId}
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{executionId}")
                .buildAndExpand(responseDTO.executionId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @Operation(summary = "Update execution status (SUCCESS/FAILED)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK --> Execution updated"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST --> Invalid request"),
            @ApiResponse(responseCode = "409", description = "CONFLICT --> Invalid status transition"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND --> Execution not found")
    })
    @PatchMapping(path = "/executions/{executionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobExecutionResponseDTO> updateExecutionStatus(
            @Positive @PathVariable Long executionId,
            @Valid @RequestBody JobExecutionStatusUpdateDTO statusUpdate
    ) {
        ExecutionStatus status = statusUpdate.status();
        log.info("Updating executionId={} to status={}", executionId, status);

        return switch (status) {
            case SUCCESS -> ResponseEntity.ok(jobExecutionService.markSuccess(executionId));
            case FAILED -> ResponseEntity.ok(jobExecutionService.markFailure(executionId, statusUpdate.errorMessage()));
            // STARTED/IN_PROGRESS are not valid final states for an update request
            case STARTED, IN_PROGRESS -> {
                log.warn("Rejected update for executionId={} to non-terminal status={}", executionId, status);
                yield ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        };
    }
}
