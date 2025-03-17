package com.englishaoe.lesson.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerStatistics {
    Long accountsRegistered;
    Long activeAccountsByWeek;
    Long activeAccountsByMonth;

    public CustomerStatistics(Long accountsRegistered, Long activeAccountsByWeek, Long activeAccountsByMonth) {
        this.accountsRegistered = accountsRegistered;
        this.activeAccountsByWeek = activeAccountsByWeek;
        this.activeAccountsByMonth = activeAccountsByMonth;
    }
}
