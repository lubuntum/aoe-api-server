package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.results.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findExamsByUserId(Long userId);
}
