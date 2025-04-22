package com.example.IntegrationService.service.batching;

import com.example.IntegrationService.model.RequestQueue;
import com.example.IntegrationService.repo.FetchData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationWriter implements ItemWriter<RequestQueue> {

    private final Logger logger = LoggerFactory.getLogger(IntegrationWriter.class);
    @Autowired
    private FetchData fetchData;
    @Override
    public void write(Chunk<? extends RequestQueue> chunk) throws Exception {
        logger.info("Writing {} items to DB", chunk.size());

        fetchData.saveAll(chunk);
    }
}
