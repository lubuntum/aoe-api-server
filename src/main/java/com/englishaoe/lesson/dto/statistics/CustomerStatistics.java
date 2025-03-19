package com.englishaoe.lesson.dto.statistics;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerStatistics {
    Long customersRegistered;
    Long currentOnline;
    Long activeCustomersByWeek;
    Long activeCustomersByMonth;

    public CustomerStatistics(Long customersRegistered, Long currentOnline, Long activeCustomersByWeek, Long activeCustomersByMonth) {
        this.customersRegistered = customersRegistered;
        this.currentOnline = currentOnline;
        this.activeCustomersByWeek = activeCustomersByWeek;
        this.activeCustomersByMonth = activeCustomersByMonth;
    }
}
