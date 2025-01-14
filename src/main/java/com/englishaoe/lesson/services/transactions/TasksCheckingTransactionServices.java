package com.englishaoe.lesson.services.transactions;

import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.TaskTypeService;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class TasksCheckingTransactionServices {
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    private CustomerServices customerServices;

    public Boolean payForChecking(Long customerId, BigDecimal price){
        return customerServices.subBalanceToCustomerById(customerId, price);
    }
    public Boolean refund(Long customerId, BigDecimal price) {
        return customerServices.addBalanceToCustomerById(customerId, price);
    }

}
