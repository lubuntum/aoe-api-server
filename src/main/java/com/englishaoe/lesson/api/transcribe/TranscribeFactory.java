package com.englishaoe.lesson.api.transcribe;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranscribeFactory {
    private final Map<String, APITranscribe> transcribeServices =  new HashMap<>();

    @Autowired
    public TranscribeFactory(ApplicationContext applicationContext) {
        Map<String, APITranscribe> beans = applicationContext.getBeansOfType(APITranscribe.class);
        for(Map.Entry<String, APITranscribe> entry: beans.entrySet()) {
            String serviceName = entry.getKey();
            transcribeServices.put(serviceName, entry.getValue());
        }
    }

    public APITranscribe getTranscribeService(String serviceName){
        return transcribeServices.get(serviceName);
    }
}
