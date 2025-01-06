package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.CheckStatus;
import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.repository.CheckStatusRepository;
import com.englishaoe.lesson.database.repository.CustomerTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (checkType.equals(TaskResultTypeEnum.EXPRESS.getTaskResultType()))
            customerTask.setExpressCheckStatusId(checkStatus.getId());
        else customerTask.setExpertCheckStatusId(checkStatus.getId());
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

}
