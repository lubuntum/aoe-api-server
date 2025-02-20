package com.englishaoe.lesson.dto.account;

import com.englishaoe.lesson.database.entity.Customer;

public class CustomerRegistrationDTOMapper {
    public static Customer parse(CustomerRegistrationDTO dto) {
        Customer customer = new Customer();
        customer.setEmail(dto.getEmail());
        customer.setName(dto.getName());
        customer.setSecondName(dto.getSecondName());
        customer.setPassword(dto.getPassword());
        customer.setRegistrationDate(dto.getRegistrationDate());
        customer.setPatronymic(dto.getPatronymic());
        customer.setIsConfirmed(false);
        return customer;
    }
}
