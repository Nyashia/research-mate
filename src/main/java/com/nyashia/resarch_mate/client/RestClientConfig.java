package com.nyashia.resarch_mate.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientConfig {

    @Value("${semantic.scholar.base-url}")
    private String semanticScholarBaseUrl;

    @Value("${semantic.scholar.api-key}")
    private String semanticScholarApiKey;

    @Bean 
    public RestClient semanticScholarRestClient() {
            RestClient.Builder builder = RestClient.builder().baseUrl(semanticScholarBaseUrl);

            if (StringUtils.hasText(semanticScholarApiKey)){
                builder.defaultHeader("x-api-key", semanticScholarApiKey);
            }

            return builder.build();
    }
    
}
