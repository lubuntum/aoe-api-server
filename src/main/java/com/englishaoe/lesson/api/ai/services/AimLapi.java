package com.englishaoe.lesson.api.ai.services;

import com.englishaoe.lesson.api.ai.AIService;
import org.springframework.beans.factory.annotation.Value;

public class AimLapi implements AIService {
    @Value("${AIMLABI_API_KEY}")
    private String aimlabiAPIKey;
    @Override
    public void sendRequest() {

    }
}
