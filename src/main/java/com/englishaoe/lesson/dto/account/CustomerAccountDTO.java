package com.englishaoe.lesson.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerAccountDTO {
    private String username;
    private String email;
    private String name;
    private String secondName;
    private String registrationDate;
    private Integer attemptsAI;
    private Integer attemptsExpert;
    //Actual date of purchasing subscription
    private String actualSubscriptionDate;
    //Name for organization or individual who invited customer
    private String partnerName;

}
