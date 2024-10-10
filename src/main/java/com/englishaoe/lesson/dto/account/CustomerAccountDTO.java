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
    private String actualSubscriptionDate;

}
