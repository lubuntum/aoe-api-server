package com.englishaoe.lesson.api.ai.services;

import com.englishaoe.lesson.api.ai.AIService;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("aimlapi")
public class AimLapi implements AIService {
    @Value("${AIMLAPI_API_KEY}")
    private String aimlabiAPIKey;
    @Override
    public String sendRequest(CustomerTaskDTO customerTaskDTO, String prompt) {
        return null;
    }
}
