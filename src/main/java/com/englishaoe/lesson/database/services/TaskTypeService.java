package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.repository.TaskTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskTypeService {
    @Autowired
    private TaskTypeRepository taskTypeRepository;

    public String getPromptByType(int type) {
        return taskTypeRepository.findPromptByType(type);
    }
}
