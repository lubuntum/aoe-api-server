package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.TaskResultType;
import com.englishaoe.lesson.database.repository.TaskResultTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskResultTypeService {
    @Autowired
    private TaskResultTypeRepository taskResultTypeRepository;

    public TaskResultType getTaskResultTypeByType(String type) {
        return taskResultTypeRepository.findByType(type);
    }

}
