package com.myapps.config;


import com.myapps.dto.JobExecutionResponseDTO;
import com.myapps.dto.JobRequestDTO;
import com.myapps.dto.JobResponseDTO;
import com.myapps.entity.Job;
import com.myapps.entity.JobExecution;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    // DTO to Entity and Entity to DTO mapping methods can be added here
    public Job map(JobRequestDTO requestDTO) {
        // Implement mapping logic here
        Job job = new Job();
        job.setJobName(requestDTO.jobName());
        job.setJobType(requestDTO.jobType());
        job.setOwnerTeam(requestDTO.ownerTeam());
        job.setEnvironment(requestDTO.environment());
        return job;
    }

   public JobResponseDTO map(Job job) {
        return new JobResponseDTO(
                job.getJobId(),
                job.getJobName(),
                job.getJobType(),
                job.getOwnerTeam(),
                job.getEnvironment()
        );
   }

   public JobExecutionResponseDTO map (JobExecution execution){
        return new JobExecutionResponseDTO(
                execution.getExecutionId(),
                execution.getJob().getJobId(),
                execution.getStatus(),
                execution.getStartTime(),
                execution.getEndTime(),
                execution.getDurationMs(),
                execution.getErrorMessage()
        );
   }

}
