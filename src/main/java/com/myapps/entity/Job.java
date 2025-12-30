package com.myapps.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(name ="job_name", nullable = false, unique = true)
    private String jobName;

    private String jobType;
    private String environment;
    private String ownerTeam;

}
