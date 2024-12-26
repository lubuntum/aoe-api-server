package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.entity.role.Role;
import com.englishaoe.lesson.database.repository.CustomerRepository;
import com.englishaoe.lesson.dto.account.CustomerAccountDTO;
import com.englishaoe.lesson.dto.account.CustomerHeaderDTO;
import com.englishaoe.lesson.dto.authorization.CustomerAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServices {
    @Autowired
    private CustomerRepository customerRepository;
    public Customer getCustomerById(Long id){
        return customerRepository.findById(id).orElse(null);
    }
    public CustomerHeaderDTO getCustomerHeaderDataById(Long id) {
        Customer customer = customerRepository.findCustomerWithRoles(id);
        if (customer == null) return null;
        List<String> roleNames = customer
                .getRoles()
                .stream()
                .map(Role::getName)
                .toList();
        return new CustomerHeaderDTO(customer.getUsername(), customer.getAttemptsAI(), customer.getAttemptsExpert(), roleNames);
        //return customerRepository.findCustomerHeaderData(id);
    }
    public boolean isCustomerHasAdminRole(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) return false;
        if (customer.getRoles() == null) return false;
        return customer.getRoles().stream().anyMatch(role -> "admin".equals(role.getName()));
    }
    public CustomerAccountDTO getCustomerAccountDataById(Long id){
        return customerRepository.findCustomerAccountData(id);
    }
    public CustomerAuthDTO getCustomerCredentialByEmail(String email){
        return customerRepository.findCustomerCredentialByEmail(email);
    }
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Customer getCustomerByEmail(String email){
        return customerRepository.findByEmail(email);
    }
    public boolean emailExists(String email) {
        return customerRepository.findByEmail(email) != null;
    }
}
