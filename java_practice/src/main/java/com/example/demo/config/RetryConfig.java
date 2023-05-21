package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;


@Configuration
public class RetryConfig {

    @Bean
    public SimpleRetryPolicy simpleRetryPolicy() {
        var simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(5);
        return simpleRetryPolicy;
    }

    @Bean
    public FixedBackOffPolicy fixedBackOffPolicy() {
        var fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(3000);
        return fixedBackOffPolicy;
    }

    @Bean
    public ExceptionClassifierRetryPolicy exceptionClassifierRetryPolicy(SimpleRetryPolicy simpleRetryPolicy) {
        var exceptionClassifierRetryPolicy = new ExceptionClassifierRetryPolicy();
        exceptionClassifierRetryPolicy.setExceptionClassifier(set -> {
            return set instanceof RuntimeException ? simpleRetryPolicy : new NeverRetryPolicy();
        });

        return exceptionClassifierRetryPolicy;
    }

    @Bean(name = "customTemplate")
    public RetryTemplate retryTemplate(ExceptionClassifierRetryPolicy exceptionClassifierRetryPolicy, FixedBackOffPolicy fixedBackOffPolicy) {
        return RetryTemplate
                .builder()
                .customPolicy(exceptionClassifierRetryPolicy)
                .customBackoff(fixedBackOffPolicy)
                .build();
    }

    @Bean(name = "normalTemplate")
    public RetryTemplate normalTemplate() {
        return RetryTemplate.
                builder()
                .maxAttempts(5)
                .fixedBackoff(3000)
                .retryOn(RuntimeException.class)
                .build();
    }
}
