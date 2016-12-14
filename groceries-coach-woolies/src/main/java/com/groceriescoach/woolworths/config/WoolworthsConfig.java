package com.groceriescoach.woolworths.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@ComponentScan(basePackages = {"com.groceriescoach.woolworths.services", "com.groceriescoach.woolworths.controllers"})
@Configuration
public class WoolworthsConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

}
