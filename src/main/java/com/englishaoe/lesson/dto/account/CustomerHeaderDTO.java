package com.englishaoe.lesson.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomerHeaderDTO {
    private String username;
    private BigDecimal currentBalance;
    private List<String> roles;
    public CustomerHeaderDTO(String username, BigDecimal currentBalance, List<String> roles){
        this.username = username;
        this.currentBalance = currentBalance;
        this.roles = roles;
    }
}
