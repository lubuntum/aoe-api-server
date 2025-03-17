package com.englishaoe.lesson.services.statistics;

import com.englishaoe.lesson.dto.statistics.PartnerStatistics;
import org.springframework.stereotype.Service;

@Service
public class PartnerStatisticsService {
    PartnerStatistics getStatisticsByPartnersActivity() {
        return new PartnerStatistics(0L, 0L, 0L);
    }
}
