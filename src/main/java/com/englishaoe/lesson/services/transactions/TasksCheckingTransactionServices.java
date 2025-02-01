package com.englishaoe.lesson.services.transactions;

import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.TaskTypeService;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.taskcheck.TaskCheckEndHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TasksCheckingTransactionServices {
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    private CustomerServices customerServices;
    @Autowired
    private CustomerTaskService customerTaskService;

    public Boolean payForChecking(Long customerId, BigDecimal price){
        return customerServices.subBalanceToCustomerById(customerId, price);
    }
    public Boolean refund(Long customerId, BigDecimal price) {
        return customerServices.addBalanceToCustomerById(customerId, price);
    }
    public void refundFailedExamForSuccessTasks(Long customerId){
        BigDecimal examPrice = taskTypeService.getTotalPrice();
        refund(customerId, examPrice);
    }

}
