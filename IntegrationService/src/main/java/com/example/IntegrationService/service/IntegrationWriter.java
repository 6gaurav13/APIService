package com.example.IntegrationService.service;

import com.example.IntegrationService.model.IntegrationConfig;
import com.example.IntegrationService.repo.FetchData;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class IntegrationWriter implements ItemWriter<IntegrationConfig> {

    @Autowired
    private FetchData fetchData;
    @Override
    public void write(Chunk<? extends IntegrationConfig> chunk) throws Exception {
        fetchData.saveAll(chunk);
    }
}
