package com.example.IntegrationService.service;

import com.example.IntegrationService.model.IntegrationConfig;
import com.example.IntegrationService.task.OperationTask;
import com.example.IntegrationService.task.OperationTaskFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class IntegrationProcessor implements ItemProcessor<IntegrationConfig, IntegrationConfig> {

    @Autowired
    private OperationTaskFactory operationTaskFactory;
    @Override
    public IntegrationConfig process(IntegrationConfig item) throws Exception {
        try{
            OperationTask task = operationTaskFactory.getTask(item.getName());
            task.setConfig(item);
            task.run();
            item.setComment("Completed");
        }
        catch(Exception e){
            item.setComment("Failed");
        }
        return item;
    }
}
