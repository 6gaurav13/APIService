package com.example.IntegrationService.service.batching;

import com.example.IntegrationService.model.ApiConfig;
import com.example.IntegrationService.model.RequestQueue;
import com.example.IntegrationService.service.requestHandling.RequestProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class IntegrationProcessor implements ItemProcessor<RequestQueue, RequestQueue> {

    private final RequestProcessor requestProcessor;

    public IntegrationProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Override
    public RequestQueue process(RequestQueue item) throws Exception {

        try{
            ApiConfig config = item.getApiConfig();
            int itemId = item.getId();
            if(config == null){
                throw new Exception("Service Name is not present in config");
            }
            requestProcessor.processRequest(config, itemId);
        }
        catch(Exception e){
            throw new Exception("Exception Occured in Process function" + e.getMessage());
        }
        return item;
    }
}
