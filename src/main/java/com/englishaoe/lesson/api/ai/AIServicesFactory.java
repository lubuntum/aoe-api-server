package com.englishaoe.lesson.api.ai;

import com.englishaoe.lesson.api.transcribe.APITranscribe;
import com.englishaoe.lesson.services.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class AIServicesFactory extends ServiceFactory<AIService>{
    @Autowired
    public AIServicesFactory(ApplicationContext applicationContext) {
        super(applicationContext, AIService.class);
    }
}
