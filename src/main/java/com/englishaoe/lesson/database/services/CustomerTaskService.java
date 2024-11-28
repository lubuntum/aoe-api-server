package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.repository.CustomerTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerTaskService {
    @Autowired
    private CustomerTaskRepository customerTaskRepository;

    public CustomerTask saveCustomerTask(CustomerTask customerTask){
        return customerTaskRepository.save(customerTask);
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
    public void updateAnswerInCustomerTask(Long customerTaskId, String answer) {
        Optional<CustomerTask> customerTaskOptional = customerTaskRepository.findById(customerTaskId);
        if (customerTaskOptional.isEmpty())
            throw new RuntimeException("CustomerTask not found with id: " + customerTaskId);
        CustomerTask customerTask = customerTaskOptional.get();
        customerTask.setAnswer(answer);
        customerTaskRepository.save(customerTask);
    }
}
