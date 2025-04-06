package com.example.IntegrationService.task;

import com.example.IntegrationService.model.IntegrationConfig;

public interface OperationTask extends Runnable {
	
	void setConfig(IntegrationConfig config);
}
