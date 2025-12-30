package com.myapps.service;

import com.myapps.domain.MsgSrc;
import com.myapps.dto.JobRequestDTO;
import com.myapps.dto.JobResponseDTO;
import com.myapps.entity.Job;
import com.myapps.exception.JobNotFoundException;
import com.myapps.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{
    private ModelMapper  modelMapper;
    private JobRepository jobRepository;
    private MsgSrc msgSrc;

    @Override
    public JobResponseDTO registerJob(JobRequestDTO requestDTO) {
        Job job = modelMapper.map(requestDTO, Job.class);
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
