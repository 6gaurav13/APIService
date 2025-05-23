package com.example.IntegrationService.config;

import com.example.IntegrationService.model.RequestQueue;
import com.example.IntegrationService.service.batching.CustomIntegrationReader;
import com.example.IntegrationService.service.batching.IntegrationProcessor;
import com.example.IntegrationService.service.batching.IntegrationWriter;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final org.slf4j.Logger logger =  LoggerFactory.getLogger(BatchConfig.class);

    //Storing meta data of jon
    private final JobRepository jobRepository;

    //For DB trasaction manage of job
    private final PlatformTransactionManager platformTransactionManager;

    //CustomReader, CustomProcessor, CustomWriter
    private final CustomIntegrationReader reader;
    private final IntegrationProcessor processor;
    private final IntegrationWriter writer;


    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, IntegrationProcessor processor, CustomIntegrationReader reader, IntegrationWriter writer) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.processor = processor;
        this.reader = reader;
        this.writer = writer;
    }

    @Bean
    public Step integrationStep() {
        logger.info("---------------Inside the integration Step----------");
        return new StepBuilder("integrationStep", jobRepository)
                .<RequestQueue, RequestQueue>chunk(10, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    @Bean
    public Job integrationJob() {
        logger.info("-----------Inside the integrationJob--------------");
        return new JobBuilder("integrationJob", jobRepository)
                .start(integrationStep())
                .build();
    }
}
