package com.example.IntegrationService.task;

import com.example.IntegrationService.repo.FetchData;
import org.springframework.stereotype.Component;

@Component
public class OperationTaskFactory {

    private final FetchData fetchData;

    public OperationTaskFactory(FetchData fetchData) {
        this.fetchData = fetchData;
    }

    public OperationTask getTask(String operationName) {
        switch (operationName.toLowerCase()) {
            case "concord":
                return new ConcordTask(fetchData); //returning it so that it can be used by taskexecutor
            case "zatca":
                return new ZatcaTask(fetchData);
            default:
                throw new IllegalArgumentException("Unsupported operation: " + operationName);
        }
    }
}
