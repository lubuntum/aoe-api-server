package com.englishaoe.lesson.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CustomerHeaderDTO {
    private String username;
    private Integer attemptsAI;
    private Integer attemptsExpert;
    private List<String> roles;
    public CustomerHeaderDTO(String username, Integer attemptsAI, Integer attemptsExpert, List<String> roles){
        this.username = username;
        this.attemptsAI = attemptsAI;
        this.attemptsExpert = attemptsExpert;
        this.roles = roles;
    }
}
