package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.variants.TaskType;
import com.englishaoe.lesson.database.repository.TaskTypeRepository;
import com.englishaoe.lesson.exceptions.RegularException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskTypeService {
    @Autowired
    private TaskTypeRepository taskTypeRepository;

    public String getPromptByType(int type) {
        return taskTypeRepository.findPromptByType(type);
    }
    public List<TaskType> getAllTaskType(){
        return taskTypeRepository.findAllTaskType();
    }
    public TaskType updateTaskType(Long id, String prompt){
        Optional<TaskType> taskTypeOptional = taskTypeRepository.findById(id);
        if (taskTypeOptional.isEmpty()) throw new RegularException("TaskType not found", 404);
        TaskType taskType = taskTypeOptional.get();
        taskType.setPrompt(prompt);
        return taskTypeRepository.save(taskType);
    }
}
