package com.myapps.service;

import com.myapps.domain.MsgSrc;
import com.myapps.dto.JobRequestDTO;
import com.myapps.dto.JobResponseDTO;
import com.myapps.entity.Job;
import com.myapps.exception.JobNotFoundException;
import com.myapps.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService{
    private final ModelMapper  modelMapper;
    private final JobRepository jobRepository;
    private final MsgSrc msgSrc;

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    public JobServiceImpl(ModelMapper modelMapper, JobRepository jobRepository, MsgSrc msgSrc) {
        this.modelMapper = modelMapper;
        this.jobRepository = jobRepository;
        this.msgSrc = msgSrc;
    }

    @Override
    public JobResponseDTO registerJob(JobRequestDTO requestDTO) {
        Job job = modelMapper.map(requestDTO, Job.class);
        log.info("Job Name: {}",job.getJobName());
        Job savedJob = jobRepository.save(job);

        return modelMapper.map(savedJob, JobResponseDTO.class);
    }

    @Override
    public JobResponseDTO getJobById (Long jobId) {
         Job job = jobRepository.findById(jobId)
                 .orElseThrow(() -> new JobNotFoundException(msgSrc.getMessage("JOB.NOT.FOUND",jobId)));
         return modelMapper.map(job, JobResponseDTO.class);
    }
}
