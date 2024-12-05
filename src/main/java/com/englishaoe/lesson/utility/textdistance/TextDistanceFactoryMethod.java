package com.englishaoe.lesson.utility.textdistance;

import com.englishaoe.lesson.taskcheck.TaskChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TextDistanceFactoryMethod {
    private final Map<String, TextDistanceMethod> taskCheckerServices = new HashMap<>();
    @Autowired
    public TextDistanceFactoryMethod(ApplicationContext applicationContext) {
        Map<String, TextDistanceMethod> beans = applicationContext.getBeansOfType(TextDistanceMethod.class);
        for(Map.Entry<String, TextDistanceMethod> entry: beans.entrySet()) {
            String serviceName = entry.getKey();
            taskCheckerServices.put(serviceName, entry.getValue());
        }
    }
    public TextDistanceMethod getTextDistanceMethod(String name) {
        return taskCheckerServices.get(name);
    }
}
