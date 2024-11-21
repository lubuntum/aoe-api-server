package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.repository.CustomerTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerTaskService {
    @Autowired
    private CustomerTaskRepository customerTaskRepository;

    public void saveCustomerTask(CustomerTask customerTask){
        customerTaskRepository.save(customerTask);
    }
    public List<CustomerTask> getCustomerTaskByExamId(Long examId){
        return customerTaskRepository.findByExamId(examId);
    }
    /** Get all customerTask completed by user out of exam, just solid task*/
    public List<CustomerTask> getCustomerTaskOutOfExam(Long taskId, Long customerId) {
        return customerTaskRepository.findByTaskIdAndCustomerIdAndExamIdIsNull(taskId, customerId);
    }
    public CustomerTask getCustomerTaskById(Long customerTaskId) {
        return customerTaskRepository.findById(customerTaskId).orElse(null);
    }
}
