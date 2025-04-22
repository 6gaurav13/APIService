package com.example.IntegrationService.task;

import com.example.IntegrationService.model.RequestQueue;
import com.example.IntegrationService.repo.FetchData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZatcaTask implements OperationTask {
    private static final Logger logger = LoggerFactory.getLogger(ZatcaTask.class);
    private RequestQueue config;
    private final FetchData fetchData;

    public ZatcaTask(FetchData fetchData) {
        this.fetchData = fetchData;
    }

    @Override
    public void setConfig(RequestQueue config) {
        this.config = config;
    }

    @Override
    public void run() {
        logger.info("Running ZatcaTask in thread {}", Thread.currentThread().getName());

        try {
            Thread.sleep(100000);
            logger.info("ZatcaTask complete for ID {}", config.getId());
        } catch (InterruptedException e) {
            logger.error("Error in ZatcaTask", e);
        }
    }
}
