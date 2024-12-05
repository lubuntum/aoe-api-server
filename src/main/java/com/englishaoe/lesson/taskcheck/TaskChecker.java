package com.englishaoe.lesson.taskcheck;

import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;

public interface TaskChecker {
    TaskResult checkTask(CustomerTaskDTO customerTaskDTO);
}
