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
    @Autowired
    CustomerTaskRepository customerTaskRepository;
    public Exam createExam(Exam exam){
        return examRepository.save(exam);
    }
    public void saveCustomerTask(CustomerTask customerTask){
        customerTaskRepository.save(customerTask);
    }
    public List<CustomerTask> getCustomerTaskByExamId(Long examId){
        return customerTaskRepository.findByExamId(examId);
    }
}
