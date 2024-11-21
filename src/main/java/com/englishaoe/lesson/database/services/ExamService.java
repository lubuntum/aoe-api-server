package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.Exam;
import com.englishaoe.lesson.database.repository.CustomerTaskRepository;
import com.englishaoe.lesson.database.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    //exam and customerTask related and connected, use in one ExamService
    @Autowired
    ExamRepository examRepository;
    public Exam createExam(Exam exam){
        return examRepository.save(exam);
    }

    public List<Exam> getExamsByCustomerId(Long customerId){
        return examRepository.findExamsByCustomerId(customerId);
    }
    public List<Exam> getExamsByCustomerIdAndVariantId(Long customerId, Long variantId){
        return examRepository.findByCustomerIdAndVariantId(customerId, variantId);
    }
}
