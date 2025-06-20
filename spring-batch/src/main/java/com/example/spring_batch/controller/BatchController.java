package com.example.spring_batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchController {
    private final JobLauncher jobLauncher;
    private final Job processCustomersJob;

    public BatchController(JobLauncher jobLauncher, Job processCustomersJob) {
        this.jobLauncher = jobLauncher;
        this.processCustomersJob = processCustomersJob;
    }

    @PostMapping("/process-customers")
    public String startJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        
        jobLauncher.run(processCustomersJob, jobParameters);
        return "Batch job has been started";
    }
} 