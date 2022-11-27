package com.inbank.creditdecisionengine.service;

import com.inbank.creditdecisionengine.dto.PersonInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ThirdPartyCommunicationService {

    private Logger logger = LoggerFactory.getLogger(CreditDecisionService.class);

    private final RestTemplate restTemplate;

    @Value("${externalService.url}")
    private String EXTERNAL_SERVICE_URL;

    public ThirdPartyCommunicationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Cacheable(value = "person-info-cachee", key = "#identityNumber")
    public PersonInfoDto getPersonInfo(String identityNumber) {
        logger.info("External service will be called for id number {} to get person info", identityNumber);
        String url = EXTERNAL_SERVICE_URL + "?identityNumber=" + identityNumber;
        return restTemplate.getForObject(url, PersonInfoDto.class);
    }
}
