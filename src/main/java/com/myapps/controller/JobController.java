package com.myapps.controller;

import com.myapps.dto.JobRequestDTO;
import com.myapps.dto.JobResponseDTO;
import com.myapps.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    ResponseEntity<JobResponseDTO> registerJob(@RequestBody JobRequestDTO requestDTO) {
        JobResponseDTO jobResponseDTO = jobService.registerJob(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponseDTO);
    }

    @GetMapping("/{jobId}")
    ResponseEntity<JobResponseDTO> getJobById(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }

}
