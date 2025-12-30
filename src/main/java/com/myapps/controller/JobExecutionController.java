package com.myapps.controller;

import com.myapps.dto.JobExecutionResponseDTO;
import com.myapps.service.JobExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobExecutionController {
    private final JobExecutionService jobExecutionService;

    @PostMapping("/{jobId}/executions/start")
    public ResponseEntity<JobExecutionResponseDTO> startExecution(@PathVariable Long jobId) {
        JobExecutionResponseDTO responseDTO = jobExecutionService.startExecution(jobId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/executions/{executionId}/success")
    public ResponseEntity<JobExecutionResponseDTO> markSuccess(@PathVariable Long executionId) {
        JobExecutionResponseDTO responseDTO = jobExecutionService.markSuccess(executionId);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/executions/{executionId}/failure")
    public ResponseEntity<JobExecutionResponseDTO> markFailure(@PathVariable Long executionId, @RequestParam String errorMessage) {
        JobExecutionResponseDTO responseDTO = jobExecutionService.markFailure(executionId, errorMessage);
        return ResponseEntity.ok(responseDTO);
    }
}
