package com.example.IntegrationService.service.requestHandling;

import com.example.IntegrationService.model.ApiConfig;
import com.example.IntegrationService.repo.FetchData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class RequestProcessor{

    private final FetchData fetchData;

    private static Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

    private final RestTemplate restTemplate;

    public RequestProcessor(RestTemplate restTemplate, FetchData fetchData) {
        this.restTemplate = restTemplate;
        this.fetchData = fetchData;
    }

    @Async("integrationTaskExecutor")
    public void processRequest(ApiConfig config, int itemId) throws Exception {
        logger.info("Processing itemId {} on thread {}", itemId, Thread.currentThread().getName());

        try{
            if (!isValidConfig(config)) {
                logger.info("API Config is Invalid");
                return;
            }
            logger.info("Current thread is -> {}", Thread.currentThread().getName() + " and service name is -> " + config.getName());
            HttpMethod httpMethod = HttpMethod.valueOf(config.getMethod().toUpperCase());
            HttpHeaders headers = buildHeaders(config.getHeaders(), httpMethod);
            HttpEntity<String> entity;

            if (httpMethod == HttpMethod.GET || httpMethod == HttpMethod.DELETE) {
                entity = buildEntity(httpMethod, headers, null); // No body
            } else {
                if (!isNotBlank(config.getRequestPayload())) {
                    logger.warn("Request payload is missing for method {} on itemId {}", httpMethod, itemId);
                    return; // or throw exception, based on your business rule
                }
                entity = buildEntity(httpMethod, headers, config.getRequestPayload());
            }

            ResponseEntity<String> response = restTemplate.exchange(
                    config.getBaseUrl(),
                    httpMethod,
                    entity,
                    String.class
            );
            logger.info("Response is {}", response);
            if(response.getStatusCode().is2xxSuccessful()){
                int updatedRows = fetchData.updateStatusAndComment(config.getId(), LocalDateTime.now(), itemId);
                if (updatedRows == 0) {
                    logger.warn("No request_queue row found with api_config_id = {}", config.getId());
                }
            }

        }
        catch (Exception e) {
            logger.error("Error occurred in RequestProcessor", e);
            throw new Exception("Error occurred in RequestProcessor", e); // preserve cause
        }
    }
    private boolean isValidConfig(ApiConfig config) {
        return config != null
                && isNotBlank(config.getBaseUrl())
                && isNotBlank(config.getMethod());
    }
    private boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
    private HttpHeaders buildHeaders(String headerJson, HttpMethod httpMethod) throws Exception {
        HttpHeaders headers = new HttpHeaders();

        if (isNotBlank(headerJson)) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> headerMap = objectMapper.readValue(headerJson, new TypeReference<>() {});
            headerMap.forEach(headers::set);
        }

        if (httpMethod != HttpMethod.GET && httpMethod != HttpMethod.DELETE) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        return headers;
    }
    private HttpEntity<String> buildEntity(HttpMethod method, HttpHeaders headers, String body) {
        return (method == HttpMethod.GET || method == HttpMethod.DELETE)
                ? new HttpEntity<>(headers)
                : new HttpEntity<>(body, headers);
    }
}
