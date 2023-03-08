package com.example.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MailConfigValue {
    @Value("${spring.mail.port}")
    private int port;
}
