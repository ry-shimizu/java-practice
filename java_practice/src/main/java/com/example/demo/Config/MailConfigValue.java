package com.example.demo.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MailConfigValue {
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;
}
