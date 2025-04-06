package com.example.IntegrationService.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.IntegrationService.IntegrationServiceApplication;
import com.example.IntegrationService.model.IntegrationConfig;
import com.example.IntegrationService.repo.FetchData;

@Service
public class BatchPolling {
    private static final Logger logger = LoggerFactory.getLogger("PollingLogger");
    @Autowired
	private FetchData fetchData;
    
    
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
            
            fetchData.updateStatusById(config.getId());
        }

    }
}
