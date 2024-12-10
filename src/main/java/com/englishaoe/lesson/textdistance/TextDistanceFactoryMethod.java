package com.englishaoe.lesson.textdistance;

import com.englishaoe.lesson.services.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class TextDistanceFactoryMethod extends ServiceFactory<TextDistanceMethod> {
    @Autowired
    public TextDistanceFactoryMethod(ApplicationContext applicationContext) {
        super(applicationContext, TextDistanceMethod.class);

    }
}
