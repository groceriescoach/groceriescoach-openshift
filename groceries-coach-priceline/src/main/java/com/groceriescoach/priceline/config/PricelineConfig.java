package com.groceriescoach.priceline.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"com.groceriescoach.priceline.service"})
@Configuration
public class PricelineConfig {
}
