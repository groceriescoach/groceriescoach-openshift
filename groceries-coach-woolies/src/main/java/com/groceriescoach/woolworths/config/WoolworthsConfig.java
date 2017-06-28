package com.groceriescoach.woolworths.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"com.groceriescoach.woolworths.services", "com.groceriescoach.woolworths.controllers"})
@Configuration
public class WoolworthsConfig {

}
