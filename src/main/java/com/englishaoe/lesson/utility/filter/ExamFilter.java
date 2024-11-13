package com.englishaoe.lesson.utility.filter;

import com.englishaoe.lesson.database.entity.results.Exam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExamFilter {
    /**
     * Get all exam where variantId is not repeated
     * needed for get all completed variants for customer
     */
    public List<Long> filterExamsByUniqueVariantId(List<Exam> exams){
        return exams.stream()
                .map(Exam::getVariantId)
                .distinct()
                .collect(Collectors.toList());
    }
}
