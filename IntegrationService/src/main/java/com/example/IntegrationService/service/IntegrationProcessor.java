package com.example.IntegrationService.service;

import com.example.IntegrationService.model.IntegrationConfig;
import com.example.IntegrationService.task.OperationTask;
import com.example.IntegrationService.task.OperationTaskFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationProcessor implements ItemProcessor<IntegrationConfig, IntegrationConfig> {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationProcessor.class);

    @Autowired
    private OperationTaskFactory operationTaskFactory;
    @Override
    public IntegrationConfig process(IntegrationConfig item) throws Exception {

        try{
            logger.info("Service Name is -> " + item.getName());
            OperationTask task = operationTaskFactory.getTask(item.getName());
            task.setConfig(item);
            task.run();
            item.setComment("Completed");
            item.setStatus("04");
        }
        catch(Exception e){
            item.setComment("Failed");
        }
        return item;
    }
}
