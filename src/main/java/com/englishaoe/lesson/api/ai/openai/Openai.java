package com.englishaoe.lesson.api.ai.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Service
public class Openai {
    public static final double DEF_TEMPERATURE = 0.2;
    public static final int DEF_RESPONSES_COUNT = 1;
    public static final int DEF_MAX_TOKENS = 3000;
    public String createJsonRequestData(
            String model,
            String prompt,
            double temperature,
            int n,
            int maxTokens) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, String>> messages = List.of(Map.of("role","user", "content", prompt));
        Map<String, Object> requestData = Map.of(
                "model", model,
                "messages", messages,
                "temperature", temperature,
                "n", n,
                "max_tokens", maxTokens,
                "extra_headers", Map.of("X_Title", "AOE Lessons Server")
        );
        return objectMapper.writeValueAsString(requestData);
    }
    public HttpRequest createHttpRequest(String jsonRequestData, String url, String apiKey){
        if (jsonRequestData == null) return null;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestData))
                .build();
        return request;
    }

}
