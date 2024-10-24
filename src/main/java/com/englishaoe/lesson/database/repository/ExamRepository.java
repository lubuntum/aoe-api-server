package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.results.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {

}
