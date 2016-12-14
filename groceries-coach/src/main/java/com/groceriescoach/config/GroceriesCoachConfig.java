package com.groceriescoach.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"com.groceriescoach.controllers", "com.groceriescoach.service"})
@Configuration
public class GroceriesCoachConfig {
}
