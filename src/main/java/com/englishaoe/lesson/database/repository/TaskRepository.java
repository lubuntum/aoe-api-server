package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.variants.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
