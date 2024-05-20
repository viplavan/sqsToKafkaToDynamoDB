package com.sqstokafkatodynamo.sqsToKafkaToDynamoDB.configuration;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.sqstokafkatodynamo.sqsToKafkaToDynamoDB"})
public class ApplicationConfiguration {
}
