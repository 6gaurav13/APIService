package com.example.IntegrationService.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.IntegrationService.model.RequestQueue;
import com.example.IntegrationService.repo.FetchData;

public class ConcordTask implements OperationTask{
	
	private static final Logger logger = LoggerFactory.getLogger("Concord logger");
	private RequestQueue config; // model or table
	private final FetchData fetchData; //Repository jpa

	@Override
	public void run() {
		logger.info("Running ConcordTask for ID {}", config.getId());
		try {
			 Thread.sleep(1000);
			 //fetchData.updateStatusById(config.getId());
			 logger.info("ConcordTask complete for ID {}", config.getId());
		}
		catch (Exception e) {
			logger.info("Error in catch "+e);
		}
		
	}
	ConcordTask(FetchData fetchData )
	{
		this.fetchData=fetchData;
	}

	@Override
	public void setConfig(RequestQueue config) {
		this.config = config;
	}
}
