package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.database.entity.results.TaskResultType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskResultRepository extends JpaRepository<TaskResult, Long> {

    List<TaskResult> findTaskResultByCustomerTaskId(Long customerTaskId);

}
