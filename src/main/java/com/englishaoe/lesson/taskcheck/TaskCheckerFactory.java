package com.englishaoe.lesson.taskcheck;

import com.englishaoe.lesson.api.transcribe.APITranscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/** Help find needed checker for specific task type
 * lets say in request we have field taskType:1, by using
 * TaskTypeConverter method which return name of the service by taskType like first
 * and then this factory find this service by name and check the task*/
@Service
public class TaskCheckerFactory {
    private final Map<String, TaskChecker> taskCheckerServices = new HashMap<>();

    @Autowired
    public TaskCheckerFactory(ApplicationContext applicationContext){
        Map<String, TaskChecker> beans = applicationContext.getBeansOfType(TaskChecker.class);
        for(Map.Entry<String, TaskChecker> entry: beans.entrySet()) {
            String serviceName = entry.getKey();
            taskCheckerServices.put(serviceName, entry.getValue());
        }
    }
    public TaskChecker getTaskCheckerService(String name) {
        return taskCheckerServices.get(name);
    }
}
