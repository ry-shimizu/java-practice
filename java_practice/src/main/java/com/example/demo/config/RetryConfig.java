package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.io.FileNotFoundException;

@Configuration
public class RetryConfig {

    @Bean
    public SimpleRetryPolicy simpleRetryPolicy() {
        var simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(5);
        return simpleRetryPolicy;
    }
    @Bean
    public ExceptionClassifierRetryPolicy exceptionClassifierRetryPolicy(SimpleRetryPolicy simpleRetryPolicy) {
        var exceptionClassifierRetryPolicy = new ExceptionClassifierRetryPolicy();
        exceptionClassifierRetryPolicy.setExceptionClassifier(set -> {
            return set instanceof FileNotFoundException ? new SimpleRetryPolicy() : new NeverRetryPolicy();
        });

        return exceptionClassifierRetryPolicy;
    }

    @Bean
    public RetryTemplate retryTemplate(ExceptionClassifierRetryPolicy exceptionClassifierRetryPolicy) {
        return RetryTemplate
                .builder()
                .customPolicy(exceptionClassifierRetryPolicy)
                .build();
    }
}
