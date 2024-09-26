package com.englishaoe.lesson.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:env.properties")
public class AppConfig {
    public static final String SECRET_KEY_PROPERTY = "SECRET_KEY";
    public static final String CLIENT_URL = "CLIENT_URL";
}
