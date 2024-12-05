package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.results.TaskResultType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskResultTypeRepository extends JpaRepository<TaskResultType, Long> {
    TaskResultType findByType(String type);
}
