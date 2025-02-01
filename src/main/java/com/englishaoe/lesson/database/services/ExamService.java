package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.*;
import com.englishaoe.lesson.database.repository.CheckStatusRepository;
import com.englishaoe.lesson.database.repository.CustomerTaskRepository;
import com.englishaoe.lesson.database.repository.ExamRepository;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {
    //exam and customerTask related and connected, use in one ExamService
    @Autowired
    ExamRepository examRepository;
    @Autowired
    CheckStatusRepository checkStatusRepository;
    public Exam getExamById(Long id){
        return examRepository.findById(id).orElseThrow(
                ()-> new RegularException("Exam not found", HttpStatus.FORBIDDEN.value()));
    }
    public void updateExpressGrade(Long id, Integer expressTotalGrade){
        examRepository.updateExamExpressTotalGrade(id, expressTotalGrade);
    }
    public Exam createExam(Exam exam){
        return examRepository.save(exam);
    }

    public List<Exam> getExamsByCustomerId(Long customerId){
        return examRepository.findExamsByCustomerId(customerId);
    }
    public List<Exam> getExamsByCustomerIdAndVariantId(Long customerId, Long variantId){
        return examRepository.findByCustomerIdAndVariantId(customerId, variantId);
    }
    public void accumulateTaskGradeForExam(int taskGrade, Long examId) {
        Optional<Exam> examOptional = examRepository.findById(examId);
        if (examOptional.isEmpty()) return;
        Exam exam = examOptional.get();
        if (exam.getExpressTotalGrade() == null)
            exam.setExpressTotalGrade(taskGrade);
        else
            exam.setExpressTotalGrade(exam.getExpressTotalGrade() + taskGrade);
        examRepository.save(exam);
    }
    public void updateExamCheckStatus(Long examId, String statusName, String checkType){
        Optional<Exam> examOptional = examRepository.findById(examId);
        CheckStatus checkStatus = checkStatusRepository.findByStatus(statusName);
        if (examOptional.isEmpty())
            throw new RuntimeException("Exam not found with id: "+ examId);
        Exam exam = examOptional.get();
        Long checkStatusId = checkStatus == null ? null : checkStatus.getId();
        if (checkType.equals(TaskResultTypeEnum.EXPRESS.getTaskResultType()))
            exam.setExpressCheckStatusId(checkStatusId);
        else
            exam.setExpertCheckStatusId(checkStatusId);
        if (checkType.equals(TaskResultTypeEnum.EXPRESS.getTaskResultType()) &&
                statusName.equals(CheckStatusEnum.COMPLETED.getStatus()))
            exam.setExpressSendDate(DateUtil.getCurrentDate());
        if (checkType.equals(TaskResultTypeEnum.EXPERT.getTaskResultType()) &&
                statusName.equals(CheckStatusEnum.COMPLETED.getStatus()))
            exam.setExpertSendDate(DateUtil.getCurrentDate());
        examRepository.save(exam);
    }
}
