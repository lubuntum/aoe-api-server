package com.englishaoe.lesson.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PartnerStatistics {
    Long partners;
    Long activePartnersByWeek;
    Long activePartnersByMonth;

    public PartnerStatistics(Long partners, Long activePartnersByWeek, Long activePartnersByMonth) {
        this.partners = partners;
        this.activePartnersByWeek = activePartnersByWeek;
        this.activePartnersByMonth = activePartnersByMonth;
    }
}
