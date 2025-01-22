package com.englishaoe.lesson.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerRegistrationDTO {
    private String email;
    private String password;
    private String name;
    private String secondName;
    private String registrationDate;
    private Boolean isPartnerProposal;
}
