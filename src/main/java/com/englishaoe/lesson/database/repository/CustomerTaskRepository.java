package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.results.CustomerTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerTaskRepository extends JpaRepository<CustomerTask, Long> {
    List<CustomerTask> findByExamId(Long examId);
}
