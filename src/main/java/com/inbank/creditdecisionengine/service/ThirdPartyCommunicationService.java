package com.inbank.creditdecisionengine.service;

import com.inbank.creditdecisionengine.config.ExternalServiceConfig;
import com.inbank.creditdecisionengine.dto.PersonInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ThirdPartyCommunicationService {

    private final RestTemplate restTemplate;
    private final ExternalServiceConfig externalServiceConfig;

    public ThirdPartyCommunicationService(RestTemplateBuilder restTemplateBuilder, ExternalServiceConfig externalServiceConfig) {
        this.restTemplate = restTemplateBuilder.build();
        this.externalServiceConfig = externalServiceConfig;

    }

    @Cacheable(value = "person-info-cachee", key = "#identityNumber")
    public PersonInfoDto getPersonInfo(String identityNumber) {
        log.info("External service will be called for id number {} to get person info", identityNumber);
        String url = externalServiceConfig.getExternalServiceUrl() + "?identityNumber=" + identityNumber;
        return restTemplate.getForObject(url, PersonInfoDto.class);
    }
}
