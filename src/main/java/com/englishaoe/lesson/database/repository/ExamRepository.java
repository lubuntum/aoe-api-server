package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.results.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findExamsByCustomerId(Long customerId);
    List<Exam> findByCustomerIdAndVariantId(Long customerId, Long variantId);
    @Modifying
    @Query("UPDATE Exam e SET e.expressTotalGrade = :expressTotalGrade " +
            "WHERE e.id = :id")
    void updateExamExpressTotalGrade(Long id, Integer expressTotalGrade);
}
