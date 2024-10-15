package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.repository.CustomerRepository;
import com.englishaoe.lesson.dto.account.CustomerAccountDTO;
import com.englishaoe.lesson.dto.account.CustomerHeaderDTO;
import com.englishaoe.lesson.dto.authorization.CustomerAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServices {
    @Autowired
    private CustomerRepository customerRepository;
    public Customer getCustomerById(Long id){
        return customerRepository.findById(id).orElse(null);
    }
    public CustomerHeaderDTO getCustomerHeaderDataById(Long id) {
        return customerRepository.findCustomerHeaderData(id);
    }
    public CustomerAccountDTO getCustomerAccountDataById(Long id){
        return customerRepository.findCustomerAccountData(id);
    }
    public CustomerAuthDTO getCustomerCredentialByUsername(String username){
        return customerRepository.findCustomerCredentialDataByUsername(username);
    }
}
