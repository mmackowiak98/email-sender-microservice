package com.api.emailsenderapi.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "emailsender")
@Getter
@Setter
@ToString
public class SMTPConfigProperties {
    Map<String,String> smtpConfig;
}
