package com.myapps.service;

import com.myapps.config.JobMapper;
import com.myapps.domain.ExecutionStatus;
import com.myapps.domain.MsgSrc;
import com.myapps.dto.JobExecutionResponseDTO;
import com.myapps.entity.Job;
import com.myapps.entity.JobExecution;
import com.myapps.exception.ExecutionNotFoundException;
import com.myapps.exception.JobNotFoundException;
import com.myapps.repository.JobExecutionRepository;
import com.myapps.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JobExecutionServiceImpl implements JobExecutionService {
    private static final Logger log = LoggerFactory.getLogger(JobExecutionServiceImpl.class);
    private final JobMapper jobMapper;
    private final JobExecutionRepository jobExecutionRepository;
    private final JobRepository jobRepository;
    private final MsgSrc msgSrc;

    @Override
    public JobExecutionResponseDTO startExecution(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(msgSrc.getMessage("JOB.NOT.FOUND",jobId)));
        JobExecution execution = new JobExecution();
        execution.setJob(job);
        execution.setStartTime(LocalDateTime.now());
        execution.setStatus(ExecutionStatus.STARTED);
        return jobMapper.map(jobExecutionRepository.save(execution));
    }


    @Override
    public JobExecutionResponseDTO markSuccess(Long executionId) {
        JobExecution execution = getExecution(executionId);
        execution.setEndTime(LocalDateTime.now());
        execution.setStatus(ExecutionStatus.SUCCESS);
        execution.setDurationMs(calculateDuration(execution));
        return jobMapper.map(jobExecutionRepository.save(execution));
    }

    @Override
    public JobExecutionResponseDTO markFailure(Long executionId, String errorMessage) {
        JobExecution execution = getExecution(executionId);
        execution.setEndTime(LocalDateTime.now());
        execution.setStatus(ExecutionStatus.FAILED);
        execution.setErrorMessage(errorMessage);
        execution.setDurationMs(calculateDuration(execution));
        return jobMapper.map(jobExecutionRepository.save(execution));
    }

    private JobExecution getExecution(Long executionId) {
        return jobExecutionRepository.findById(executionId)
                .orElseThrow(() -> new ExecutionNotFoundException(msgSrc.getMessage("EXECUTION.NOT.FOUND",executionId)));

    }

    private Long calculateDuration(JobExecution execution) {
        LocalDateTime start = execution.getStartTime();
        LocalDateTime end = execution.getEndTime();
        if (start == null || end == null) {
            log.warn("Cannot calculate duration for execution {}: start or end time is null", execution.getExecutionId());
            return null;
        }
        long millis = Duration.between(start, end).toMillis();
        // Ensure non-negative duration in case clocks are adjusted or times are swapped
        return Math.max(0L, millis);
    }

}
