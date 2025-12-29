package com.myapps.entity;

import jakarta.persistence.*;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(nullable = false, unique = true)
    private String jobName;

    private String jobType;
    private String ownerTeam;
    private String environment;

}
