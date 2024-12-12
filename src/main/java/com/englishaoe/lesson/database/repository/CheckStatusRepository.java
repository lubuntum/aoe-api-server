package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.results.CheckStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckStatusRepository extends JpaRepository<CheckStatus, Long> {
    CheckStatus findByStatus(String status);
}
