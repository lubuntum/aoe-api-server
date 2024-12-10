package com.englishaoe.lesson.api.transcribe;

import com.englishaoe.lesson.services.ServiceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranscribeFactory extends ServiceFactory<APITranscribe> {

    @Autowired
    public TranscribeFactory(ApplicationContext applicationContext) {
        super(applicationContext, APITranscribe.class);
    }
}
