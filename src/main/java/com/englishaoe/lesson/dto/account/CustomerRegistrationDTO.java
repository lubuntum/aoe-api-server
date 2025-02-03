package com.englishaoe.lesson.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerRegistrationDTO {
    private Long customerId;
    private String email;
    private String password;
    private String name;
    private String secondName;
    private String patronymic;
    private String phoneNumber;
    private String registrationDate;
    private Boolean isPartnerProposal;
}
