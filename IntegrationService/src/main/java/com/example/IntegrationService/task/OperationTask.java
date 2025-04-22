package com.example.IntegrationService.task;

import com.example.IntegrationService.model.RequestQueue;

public interface OperationTask extends Runnable {
	
	void setConfig(RequestQueue config);
}
