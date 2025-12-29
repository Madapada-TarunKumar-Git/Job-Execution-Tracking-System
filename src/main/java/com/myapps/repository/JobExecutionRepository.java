package com.myapps.repository;

import com.myapps.entity.JobExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobExecutionRepository extends JpaRepository<JobExecution, Long> {
    List<JobExecution> findByJob_JobId(Long jobId);
}
