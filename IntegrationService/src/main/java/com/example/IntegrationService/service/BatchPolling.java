package com.example.IntegrationService.service;

import java.util.List;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.IntegrationService.IntegrationServiceApplication;
import com.example.IntegrationService.model.IntegrationConfig;
import com.example.IntegrationService.repo.FetchData;
import com.example.IntegrationService.task.OperationTask;
import com.example.IntegrationService.task.OperationTaskFactory;

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

        try{
            logger.info("Launching Spring Batch job");
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(integrationJob, jobParameters);
        }
        catch(Exception e){
            logger.error("Failed to excute batch job", e);
        }
    }
}
