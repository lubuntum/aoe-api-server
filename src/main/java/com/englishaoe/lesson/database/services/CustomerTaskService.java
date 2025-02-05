package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.CheckStatus;
import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.repository.CheckStatusRepository;
import com.englishaoe.lesson.database.repository.CustomerTaskRepository;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerTaskService {
    @Autowired
    private CustomerTaskRepository customerTaskRepository;
    @Autowired
    private CheckStatusRepository checkStatusRepository;
    /**get all customerTask data by customer id*/
    public List<Variant> getUniqueVariantsByCustomerId(Long customerId){
        return customerTaskRepository.findUniqueVariantsByCustomerId(customerId);
    }

    public CustomerTask saveCustomerTask(CustomerTask customerTask){
        return customerTaskRepository.save(customerTask);
    }
    public List<CustomerTask> getCustomerTasksByExamId(Long examId){
        return customerTaskRepository.findByExamId(examId);
    }
    /** Get all customerTask completed by user out of exam, just solid task*/
    public List<CustomerTask> getCustomerTaskOutOfExam(Long taskId, Long customerId) {
        return customerTaskRepository.findByTaskIdAndCustomerIdAndExamIdIsNull(taskId, customerId);
    }
    public CustomerTask getCustomerTaskById(Long customerTaskId) {
        return customerTaskRepository.findById(customerTaskId).orElse(null);
    }
    public void updateAnswerInCustomerTask(Long customerTaskId, String answer) {
        Optional<CustomerTask> customerTaskOptional = customerTaskRepository.findById(customerTaskId);
        if (customerTaskOptional.isEmpty())
            throw new RuntimeException("CustomerTask not found with id: " + customerTaskId);
        CustomerTask customerTask = customerTaskOptional.get();
        customerTask.setAnswer(answer);
        customerTaskRepository.save(customerTask);
    }
    public void updateCheckStatus(Long customerTaskId, String statusName, String checkType) {
        Optional<CustomerTask> customerTaskOptional = customerTaskRepository.findById(customerTaskId);
        CheckStatus checkStatus = checkStatusRepository.findByStatus(statusName);
        if (customerTaskOptional.isEmpty())
            throw new RuntimeException("CustomerTask not found with id: " + customerTaskId);
        CustomerTask customerTask = customerTaskOptional.get();
        Long checkStatusId = checkStatus == null ? null : checkStatus.getId();
        if (checkType.equals(TaskResultTypeEnum.EXPRESS.getTaskResultType()))
            customerTask.setExpressCheckStatusId(checkStatusId);
        else customerTask.setExpertCheckStatusId(checkStatusId);
        customerTaskRepository.save(customerTask);
    }
    public void updateTempCheckingData(Long customerTaskId, String checkingData){
        Optional<CustomerTask> customerTaskOptional = customerTaskRepository.findById(customerTaskId);
        if (customerTaskOptional.isEmpty())
            throw new RuntimeException("CustomerTask not found with id: " + customerTaskId);
        CustomerTask customerTask = customerTaskOptional.get();
        customerTask.setTempCheckingData(checkingData);
        customerTaskRepository.save(customerTask);
    }
    public CustomerTask getCustomerTaskByStatus(String status){
        return customerTaskRepository.findCustomerTaskByStatus(status);
    }
    public CustomerTask getOldestCustomerTaskByStatus(String status){
        return customerTaskRepository.findOldestCustomerTaskWithStatus(status);
    }
    /**
     * Check if examen is completed (all his tasks must be also completed or insufficient)
     * completed if task was transcribed and checked successfully (count even if one or more tasks was speechless)
     * */
    public boolean isAllCustomerTasksCheckedForExam(Long examId) {
        CheckStatus checkCompletedStatus = checkStatusRepository.findByStatus(CheckStatusEnum.COMPLETED.getStatus());
        if (checkCompletedStatus == null) throw new RuntimeException("Status " + CheckStatusEnum.COMPLETED.getStatus() + " not found");
        Long totalTasks = customerTaskRepository.countByExamId(examId);
        Long completedTasks = customerTaskRepository.countByExamIdAndCheckStatusId(examId, checkCompletedStatus.getId());
        return Objects.equals(totalTasks, completedTasks);
    }
    public List<CustomerTask> getCustomerTasksByExamIdAndStatus(Long examId, CheckStatusEnum checkStatusEnum) {
        return customerTaskRepository.findCustomerTaskByExamIdAndCheckStatus(examId, checkStatusEnum.getStatus());
    }

}
