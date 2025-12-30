package com.myapps.service;

import com.myapps.config.JobMapper;
import com.myapps.domain.MsgSrc;
import com.myapps.dto.JobRequestDTO;
import com.myapps.dto.JobResponseDTO;
import com.myapps.entity.Job;
import com.myapps.exception.DuplicateResourceException;
import com.myapps.exception.JobNotFoundException;
import com.myapps.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{
    private final JobMapper jobMapper;
    private final JobRepository jobRepository;
    private final MsgSrc msgSrc;

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    @Override
    public JobResponseDTO registerJob(JobRequestDTO requestDTO) {
        Job job = jobMapper.map(requestDTO);
        if (jobRepository.findByJobName(requestDTO.jobName()).isPresent()) {
            throw new DuplicateResourceException(msgSrc.getMessage("JOB.ALREADY.EXISTS", requestDTO.jobName()));
        }
        log.info("Job Name: {}",job.getJobName());
        return jobMapper.map(jobRepository.save(job));
    }

    @Override
    public JobResponseDTO getJobById (Long jobId) {
         Job job = jobRepository.findById(jobId)
                 .orElseThrow(() -> new JobNotFoundException(msgSrc.getMessage("JOB.NOT.FOUND",jobId)));
         return jobMapper.map(job);
    }
}
