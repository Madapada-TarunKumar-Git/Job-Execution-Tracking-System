package com.myapps.service;

import com.myapps.dto.JobExecutionResponseDTO;

public interface JobExecutionService {
    JobExecutionResponseDTO startExecution(Long jobId);

    JobExecutionResponseDTO markSuccess(Long executionId);

    JobExecutionResponseDTO markFailure(Long executionId, String errorMessage);
}
