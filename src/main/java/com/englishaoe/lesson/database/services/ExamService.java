package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.Exam;
import com.englishaoe.lesson.database.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ExamService {
    @Autowired
    ExamRepository examRepository;
    public Exam createExam(Exam exam){
        return examRepository.save(exam);
    }
}
