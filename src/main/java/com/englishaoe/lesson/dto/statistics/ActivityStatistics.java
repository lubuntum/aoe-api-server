package com.englishaoe.lesson.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivityStatistics {

    CustomerStatistics customerStatistics;
    PartnerStatistics partnerStatistics;

    public ActivityStatistics(CustomerStatistics customerStatistics, PartnerStatistics partnerStatistics) {
        this.customerStatistics = customerStatistics;
        this.partnerStatistics = partnerStatistics;
    }
}
