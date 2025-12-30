package com.myapps.config;


import com.myapps.dto.JobRequestDTO;
import com.myapps.dto.JobResponseDTO;
import com.myapps.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    // DTO to Entity and Entity to DTO mapping methods can be added here
    public Job requestToEntity(JobRequestDTO requestDTO) {
        // Implement mapping logic here
        Job job = new Job();
        job.setJobName(requestDTO.jobName());
        job.setJobType(requestDTO.jobType());
        job.setOwnerTeam(requestDTO.ownerTeam());
        job.setEnvironment(requestDTO.environment());
        return job;
    }

   public JobResponseDTO entityToResponse(Job job) {
        return new JobResponseDTO(
                job.getJobId(),
                job.getJobName(),
                job.getJobType(),
                job.getOwnerTeam(),
                job.getEnvironment()
        );
   }

}
