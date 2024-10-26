package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.results.CustomerTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTaskRepository extends JpaRepository<CustomerTask, Long> {
}
