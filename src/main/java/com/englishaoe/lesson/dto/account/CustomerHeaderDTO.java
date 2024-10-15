package com.englishaoe.lesson.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CustomerHeaderDTO {
    private String username;
    private Integer attemptsAI;
    private Integer attemptsExpert;
    public CustomerHeaderDTO(String username, Integer attemptsAI, Integer attemptsExpert){
        this.username = username;
        this.attemptsAI = attemptsAI;
        this.attemptsExpert = attemptsExpert;
    }
}
