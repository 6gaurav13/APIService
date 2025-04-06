package com.example.IntegrationService.task;

import com.example.IntegrationService.model.IntegrationConfig;
import com.example.IntegrationService.repo.FetchData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZatcaTask implements OperationTask {
    private static final Logger logger = LoggerFactory.getLogger(ZatcaTask.class);
    private IntegrationConfig config;
    private final FetchData fetchData;

    public ZatcaTask(FetchData fetchData) {
        this.fetchData = fetchData;
    }

    @Override
    public void setConfig(IntegrationConfig config) {
        this.config = config;
    }

    @Override
    public void run() {
        logger.info("Running ZatcaTask for ID {}", config.getId());
        // simulate processing
        try {
            Thread.sleep(1000);
            fetchData.updateStatusById(config.getId());
            logger.info("ZatcaTask complete for ID {}", config.getId());
        } catch (InterruptedException e) {
            logger.error("Error in ZatcaTask", e);
        }
    }
}
