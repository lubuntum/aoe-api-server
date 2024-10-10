package com.englishaoe.lesson.dto.account;

import com.englishaoe.lesson.database.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerAccountMapper {
    public CustomerAccountDTO toDTO(Customer customer){
        //Write a method in repository which get
        //actual transaction for customer by date
        //and use it there
        //Example customer.getTokenPackageTransactions() => List<TokenPackageTransaction> transactions
        //PackageTransactionRepository.getActualTransaction(transactions); // find it
        return new CustomerAccountDTO(customer.getUsername()
                , customer.getEmail(),customer.getName(),customer.getSecondName(),
                customer.getRegistrationDate(), customer.getAttemptsAI(), customer.getAttemptsExpert(), "28.09.2024");
    }
}
