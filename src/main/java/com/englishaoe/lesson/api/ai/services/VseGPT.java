package com.englishaoe.lesson.api.ai.services;

import com.englishaoe.lesson.api.ai.AIService;
import com.englishaoe.lesson.api.ai.openai.Openai;
import com.englishaoe.lesson.dto.results.CheckDTO;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service("vsegpt")
public class VseGPT implements AIService {
    @Value("${VSEGPT_API_KEY}")
    private String vsegptAPIKey;
    @Value("${VSEGPT_URL}")
    private String vsegptUrl;
    @Autowired
    Openai openai;
    @Override
    public String sendRequest(CustomerTaskDTO customerTaskDTO, String prompt) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonRequestData = openai.createJsonRequestData(
                    customerTaskDTO.getAiModelName(),
                    prompt,
                    Openai.DEF_TEMPERATURE,
                    Openai.DEF_RESPONSES_COUNT,
                    Openai.DEF_MAX_TOKENS);
            HttpRequest request = openai.createHttpRequest(jsonRequestData, vsegptUrl, vsegptAPIKey);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            Map<String, Object> responseData = objectMapper.readValue(responseBody, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseData.get("choices");
            if (choices == null || choices.isEmpty()){
                System.err.print("No choices found");
                return null;
            }
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");

        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        return null;
    }
}
/*
*
* */