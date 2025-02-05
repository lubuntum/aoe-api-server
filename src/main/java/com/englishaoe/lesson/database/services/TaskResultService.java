package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.database.repository.TaskResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskResultService {
    @Autowired
    private TaskResultRepository taskResultRepository;

    @Transactional
    public TaskResult saveTaskResult(TaskResult taskResult) {
        return taskResultRepository.save(taskResult);
    }
    public List<TaskResult> getTaskResultByCustomerTaskId(Long customerTaskId){
        return taskResultRepository.findTaskResultByCustomerTaskId(customerTaskId);
    }
    @Transactional
    public void deleteTaskResult(TaskResult taskResult) {
        taskResultRepository.delete(taskResult);
    }
}
