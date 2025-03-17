package com.englishaoe.lesson.controllers.statistics;

import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.dto.statistics.ActivityStatistics;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.services.AuthorizationService;
import com.englishaoe.lesson.services.statistics.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activity-statistics")
public class ActivityStatisticsController {
    @Autowired
    CustomerServices customerServices;
    @Autowired
    Statistics statistics;
    @Autowired
    AuthorizationService authorizationService;
    @GetMapping
    public ResponseEntity<ActivityStatistics> getActivityStatistics(@RequestHeader("Authorization") String token){

        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());

        return ResponseEntity.ok(statistics.getActivityStatistics());
    }
}
