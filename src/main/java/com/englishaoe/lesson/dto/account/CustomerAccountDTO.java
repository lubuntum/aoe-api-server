package com.englishaoe.lesson.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CustomerAccountDTO {
    private String username;
    private String email;
    private String name;
    private String secondName;
    private String registrationDate;
    private BigDecimal currentBalance;
    //Actual date of purchasing subscription
    private String purchaseSubDate;
    private String expireSubDate;
    //Name for organization or individual who invited customer
    private String partnerName;

}
