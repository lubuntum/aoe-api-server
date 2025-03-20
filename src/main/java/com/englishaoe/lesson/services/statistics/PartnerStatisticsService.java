package com.englishaoe.lesson.services.statistics;

import com.englishaoe.lesson.database.entity.role.RoleEnum;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.PartnerService;
import com.englishaoe.lesson.dto.statistics.PartnerStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PartnerStatisticsService {
    @Autowired
    CustomerServices customerServices;
    @Autowired
    PartnerService partnerService;
    PartnerStatistics getStatisticsByPartnersActivity() {
        return new PartnerStatistics(
                partnerService.getAllApprovedPartnerCount(),
                customerServices.getCountLoginCustomerByDateAndRole(LocalDateTime.now().minusWeeks(1), RoleEnum.PARTNER),
                customerServices.getCountLoginCustomerByDateAndRole(LocalDateTime.now().minusMonths(1), RoleEnum.PARTNER));
    }
}
