package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.variants.Task;
import com.englishaoe.lesson.database.entity.variants.TaskType;
import com.englishaoe.lesson.database.entity.variants.Variant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestVariantsGeneratorServices {
    public List<Variant> generateVariants(int count){
        return null;
    }
    private List<Task> generateTasksForVariant(){
        return null;
    }
    private List<TaskType> generateTaskTypeForTask(){
        return null;
    }
}
