package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.database.repository.TaskResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskResultService {
    @Autowired
    private TaskResultRepository taskResultRepository;

    public TaskResult saveTaskResult(TaskResult taskResult) {
        return taskResultRepository.save(taskResult);
    }
}
