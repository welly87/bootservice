package com.tambunan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({ @PropertySource(value = "classpath:bootservice.properties", ignoreResourceNotFound = false) })
@Configuration
public class BootservicePropertyConfig {

}
