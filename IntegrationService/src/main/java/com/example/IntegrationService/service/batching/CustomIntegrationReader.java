package com.example.IntegrationService.service.batching;

import com.example.IntegrationService.model.RequestQueue;
import com.example.IntegrationService.repo.FetchData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class CustomIntegrationReader implements ItemReader<RequestQueue> {

    private final Logger logger = LoggerFactory.getLogger(CustomIntegrationReader.class);
    private final FetchData fetchData;

    private Iterator<RequestQueue>iterator;

    public CustomIntegrationReader(FetchData fetchData){
        this.fetchData = fetchData;
    }
    @Override
    public RequestQueue read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(iterator == null || !iterator.hasNext()){
            List<RequestQueue> list = fetchData.findTop10ByStatus(PageRequest.of(0,10));
            logger.info("Fetched {} items for processing", list.size());

            list.forEach(config -> {
                config.setComment("In Progress");
                config.setStatus("03");
            });
            fetchData.saveAll(list);
            iterator = list.iterator();
        }
        return iterator.hasNext() ? iterator.next() : null;
    }
}
