package com.myapps.service;

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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JobExecutionServiceImpl implements JobExecutionService {
    private ModelMapper modelMapper;
    private JobExecutionRepository jobExecutionRepository;
    private JobRepository jobRepository;
    private MsgSrc msgSrc;

    @Override
    public JobExecutionResponseDTO startExecution(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(msgSrc.getMessage("JOB.NOT.FOUND",jobId)));
        JobExecution execution = new JobExecution();
        execution.setJob(job);
        execution.setStartTime(LocalDateTime.now());
        execution.setStatus(ExecutionStatus.STARTED);
        JobExecution savedExecution = jobExecutionRepository.save(execution);
        return modelMapper.map(savedExecution, JobExecutionResponseDTO.class);
    }

    @Override
    public JobExecutionResponseDTO markSuccess(Long executionId) {
        JobExecution execution = getExecution(executionId);
        execution.setEndTime(LocalDateTime.now());
        execution.setStatus(ExecutionStatus.SUCCESS);
        execution.setDurationMs(calculateDuration(execution));
        JobExecution savedExecution = jobExecutionRepository.save(execution);
        return modelMapper.map(savedExecution, JobExecutionResponseDTO.class);
    }

    @Override
    public JobExecutionResponseDTO markFailure(Long executionId, String errorMessage) {
        JobExecution execution = getExecution(executionId);
        execution.setEndTime(LocalDateTime.now());
        execution.setStatus(ExecutionStatus.FAILED);
        execution.setErrorMessage(errorMessage);
        execution.setDurationMs(calculateDuration(execution));
        JobExecution savedExecution = jobExecutionRepository.save(execution);
        return modelMapper.map(savedExecution, JobExecutionResponseDTO.class);
    }

    private JobExecution getExecution(Long executionId) {
        return jobExecutionRepository.findById(executionId)
                .orElseThrow(() -> new ExecutionNotFoundException(msgSrc.getMessage("EXECUTION.NOT.FOUND",executionId)));

    }

    private Long calculateDuration(JobExecution execution) {
        return Duration.between(execution.getStartTime(),
                execution.getEndTime())
                .toMillis();
    }

}

