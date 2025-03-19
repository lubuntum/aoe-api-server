package com.englishaoe.lesson.services.statistics;

import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.dto.statistics.ActivityStatistics;
import com.englishaoe.lesson.dto.statistics.CustomerStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerStatisticsService {
    @Autowired
    CustomerServices customerServices;
    CustomerStatistics getStatisticsByCustomersActivity(){
        return new CustomerStatistics(
                customerServices.getCountRegisteredCustomers(),
                customerServices.getCountLoginCustomerByDate(LocalDateTime.now().minusHours(3)),
                customerServices.getCountLoginCustomerByDate(LocalDateTime.now().minusWeeks(1)),
                customerServices.getCountLoginCustomerByDate(LocalDateTime.now().minusMonths(1))
        );
    }
}
