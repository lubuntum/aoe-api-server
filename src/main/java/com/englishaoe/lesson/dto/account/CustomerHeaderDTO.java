package com.englishaoe.lesson.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerHeaderDTO {
    private String username;
    private Integer attemptsAI;
    private Integer attemptsExpert;
}
