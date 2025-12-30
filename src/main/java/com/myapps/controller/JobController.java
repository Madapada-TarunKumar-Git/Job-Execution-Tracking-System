package com.myapps.controller;

import com.myapps.dto.JobRequestDTO;
import com.myapps.dto.JobResponseDTO;
import com.myapps.service.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
//write swagger doc for the class
@Tag(name = "Job Management", description = "APIs for managing jobs")
public class JobController {
    private final JobService jobService;

    @PostMapping
    ResponseEntity<JobResponseDTO> registerJob(@Valid @RequestBody JobRequestDTO requestDTO) {
        JobResponseDTO jobResponseDTO = jobService.registerJob(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponseDTO);
    }

    @GetMapping("/{jobId}")
    ResponseEntity<JobResponseDTO> getJobById(@NotNull @PathVariable Long jobId) {
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }

}
