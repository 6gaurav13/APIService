package com.example.IntegrationService.service;

import com.example.IntegrationService.model.IntegrationConfig;
import com.example.IntegrationService.repo.FetchData;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

public class CustomIntegrationReader implements ItemReader<IntegrationConfig> {

    private final FetchData fetchData;

    private Iterator<IntegrationConfig>iterator;

    public CustomIntegrationReader(FetchData fetchData){
        this.fetchData = fetchData;
    }
    @Override
    public IntegrationConfig read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(iterator == null || !iterator.hasNext()){
            List<IntegrationConfig> list = fetchData.findTop10ByStatus();
            list.forEach(config -> config.setComment("In Progress"));
            fetchData.saveAll(list);
            iterator = list.iterator();
        }
        return iterator.hasNext() ? iterator.next() : null;
    }
}
