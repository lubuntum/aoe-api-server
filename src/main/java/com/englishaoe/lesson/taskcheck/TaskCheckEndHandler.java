package com.englishaoe.lesson.taskcheck;

import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.Exam;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.ExamService;
import com.englishaoe.lesson.database.services.TaskTypeService;
import com.englishaoe.lesson.dto.results.CheckDTO;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.services.transactions.PartnerRevenueTransactionalServices;
import com.englishaoe.lesson.services.transactions.TasksCheckingTransactionServices;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TaskCheckEndHandler {
    @Autowired
    ExamService examService;
    @Autowired
    CustomerTaskService customerTaskService;
    @Autowired
    TaskTypeService taskTypeService;
    @Autowired
    TasksCheckingTransactionServices tasksCheckingTransactionServices;
    @Autowired
    PartnerRevenueTransactionalServices partnerRevenueTransactionalServices;
    public void completeChecking(CustomerTaskDTO customerTaskDTO, CheckDTO checkDTO){
        completeTask(customerTaskDTO);
        if (customerTaskDTO.getExamId() != null)
            accumulateGradeForExam(customerTaskDTO, checkDTO);
        if (customerTaskDTO.getExamId() != null && customerTaskService.isAllCustomerTasksCheckedForExam(customerTaskDTO.getExamId()))
            completeExam(customerTaskDTO);
    }
    private void completeTask(CustomerTaskDTO customerTaskDTO) {
        customerTaskService.updateCheckStatus(
                customerTaskDTO.getId(),
                CheckStatusEnum.COMPLETED.getStatus(),
                TaskResultTypeEnum.EXPRESS.getTaskResultType());
        customerTaskService.updateTempCheckingData(customerTaskDTO.getId(), null);
        if (customerTaskDTO.getExamId() == null)
            partnerRevenueTransactionalServices
                    .addRevenueForPartnerByCustomer(
                            customerTaskDTO.getCustomerId(),
                            customerTaskDTO.getTask().getTaskType());
    }
    private void completeExam(CustomerTaskDTO customerTaskDTO) {
        partnerRevenueTransactionalServices
                .addRevenueForPartnerByCustomer(
                        customerTaskDTO.getCustomerId(), null);
        examService.updateExamCheckStatus(
                customerTaskDTO.getExamId(),
                CheckStatusEnum.COMPLETED.getStatus(),
                TaskResultTypeEnum.EXPRESS.getTaskResultType());
    }
    private void accumulateGradeForExam(CustomerTaskDTO customerTaskDTO, CheckDTO checkDTO) {
        examService.accumulateTaskGradeForExam(checkDTO.getGrade(), customerTaskDTO.getExamId());
    }

    public void failTaskCheck(CustomerTaskDTO customerTaskDTO){
        if (customerTaskDTO.getExamId() == null){
            tasksCheckingTransactionServices.refund(
                    customerTaskDTO.getCustomerId(),
                    taskTypeService.getPriceByTaskType(customerTaskDTO.getTask().getTaskType()));
            customerTaskService.updateCheckStatus(
                    customerTaskDTO.getId(),
                    CheckStatusEnum.INCOMPLETE.getStatus(),
                    TaskResultTypeEnum.EXPRESS.getTaskResultType());
        }
        if (customerTaskDTO.getExamId() != null){
            //This means error for this exam already occurred somewhere and no need for refund and etc
            Exam exam = examService.getExamById(customerTaskDTO.getExamId());
            if(exam.getExpressCheckStatus().getStatus()
                    .equals(CheckStatusEnum.INCOMPLETE.getStatus()))
                return;
            examService.updateExamCheckStatus(
                    customerTaskDTO.getExamId(),
                    CheckStatusEnum.INCOMPLETE.getStatus(),
                    TaskResultTypeEnum.EXPRESS.getTaskResultType());
            examService.updateExpressGrade(exam.getId(), null);
            tasksCheckingTransactionServices
                    .refundFailedExamForSuccessTasks(customerTaskDTO.getCustomerId());
            customerTaskService.getCustomerTasksByExamId(customerTaskDTO.getExamId())
                    .forEach(cT ->
                            customerTaskService.updateCheckStatus(
                                    cT.getId(),
                                    CheckStatusEnum.INCOMPLETE.getStatus(),
                                    TaskResultTypeEnum.EXPRESS.getTaskResultType()));
        }
    }

}
