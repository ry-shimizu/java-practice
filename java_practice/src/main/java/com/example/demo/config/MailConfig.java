package com.example.demo.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix="spring.mail")
public class MailConfig {
    private final String host;
    private final int port;
    private final String userName;
    private final String password;
}
