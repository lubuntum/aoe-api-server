package com.englishaoe.lesson.services.statistics;

import com.englishaoe.lesson.dto.statistics.ActivityStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Statistics {
    @Autowired
    CustomerStatisticsService customerStatisticsService;
    @Autowired
    PartnerStatisticsService partnerStatisticsService;
    public ActivityStatistics getActivityStatistics() {
        return new ActivityStatistics(
                customerStatisticsService.getStatisticsByCustomersActivity(),
                partnerStatisticsService.getStatisticsByPartnersActivity()
        );
    }

    //public getTransactionsStatistics

}
