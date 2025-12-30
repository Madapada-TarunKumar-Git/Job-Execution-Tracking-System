package com.myapps.service;

import com.myapps.dto.JobRequestDTO;
import com.myapps.dto.JobResponseDTO;

public interface JobService {
    JobResponseDTO registerJob(JobRequestDTO requestDTO);
    JobResponseDTO getJobById(Long jobId);
}
