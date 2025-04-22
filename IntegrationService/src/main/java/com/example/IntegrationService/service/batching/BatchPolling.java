package com.example.IntegrationService.service.batching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BatchPolling {
    private static final Logger logger = LoggerFactory.getLogger("PollingLogger");

    private final JobLauncher jobLauncher;
    private final Job integrationJob;

    public BatchPolling(Job integrationJob, JobLauncher jobLauncher) {
        this.integrationJob = integrationJob;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(fixedRate = 5000) //for every 5 sec
    public void executeTask(){
        logger.info("Triggering batch job at {}", LocalDateTime.now());
        try{
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(integrationJob, jobParameters);
        }
        catch(Exception e){
            logger.error("Failed to execute batch job", e);
        }
    }
}
