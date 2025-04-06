package com.example.IntegrationService.service;

import java.util.List;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
	private FetchData fetchData;
    
    @Autowired 
    private Executor integrationTaskExecutor; //what is happening her is spring is identifying bean that is an executor bean and injecting it here.. if there are more than one bean that return executor then use @Qualifier
    
    @Autowired // autowired to call its constructor automatically and findig fetchData bean and inserting or injecting it
    private OperationTaskFactory operationTaskFactory; //for bifercating different tasks
    
    
    @Scheduled(fixedRate = 5000) //for every 5 sec
    public void executeTask(){
    	
    	List<IntegrationConfig> conf = fetchData.findByStatus("01");
        logger.info("Executing task at polling ");
        
        for(IntegrationConfig config:conf)
        {
            logger.info("Config: name={}, endpoint={}, request={}, response={}, status={}",
                    config.getName(),
                    config.getEndpoint(),
                    config.getRequest(),
                    config.getResponse(),
                    config.getStatus());
            try {
            OperationTask task = operationTaskFactory.getTask(config.getName()); //OperationTask can be said as braoder task it can be concord zatca etc etc they collectively can be said as OperationTask
            task.setConfig(config); // operationTaskFactory.getTask(config.getName()) will go to getTask and call concord or zatca and returns it which is ultimately extended from OperationTask interface only
            integrationTaskExecutor.execute(task);
            }
            catch (Exception e) {
				// TODO: handle exception
            	logger.warn("Skipping unknown operation: {}", config.getName());
			}
       
        }

    }
}
