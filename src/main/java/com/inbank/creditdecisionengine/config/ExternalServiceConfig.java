package com.inbank.creditdecisionengine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalServiceConfig {

    @Value("${externalService.url}")
    private String externalServiceUrl;

    public String getExternalServiceUrl() {
        return externalServiceUrl;
    }

    public void setExternalServiceUrl(String externalServiceUrl) {
        this.externalServiceUrl = externalServiceUrl;
    }
}
