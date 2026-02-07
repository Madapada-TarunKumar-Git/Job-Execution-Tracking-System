package com.myapps.controller;

import com.myapps.dto.JobRequestDTO;
import com.myapps.dto.JobResponseDTO;
import com.myapps.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name = "Job Management", description = "APIs for managing jobs")
@Slf4j
public class JobController {
    private final JobService jobService;

    @Operation(summary = "Register a new job")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Job created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobResponseDTO> registerJob(@Valid @RequestBody JobRequestDTO requestDTO) {
        log.info("Registering job name={} type={}", requestDTO.jobName(), requestDTO.jobType());
        JobResponseDTO jobResponseDTO = jobService.registerJob(requestDTO);

        // Location: /api/v1/jobs/{jobId}
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{jobId}")
                .buildAndExpand(jobResponseDTO.jobId())
                .toUri();

        return ResponseEntity.created(location).body(jobResponseDTO);
    }

    @Operation(summary = "Get job by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job found"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    @GetMapping(path = "/{jobId}")
    public ResponseEntity<JobResponseDTO> getJobById(@Positive @PathVariable Long jobId) {
        log.debug("Fetching job by id={}", jobId);
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }

}
