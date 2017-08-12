package com.groceriescoach.target.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"com.groceriescoach.target.service"})
@Configuration
public class TargetConfig {
}
