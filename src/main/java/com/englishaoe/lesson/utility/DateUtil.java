package com.englishaoe.lesson.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static String getCurrentDate() {
        LocalDate localDate = LocalDate.now();
        return formatter.format(localDate);
    }
    public static String getExpireDate(int months){
        LocalDate localDate = LocalDate.now();
        LocalDate expireSubscriptionDate = localDate.plusDays(months* 30L);
        return formatter.format(expireSubscriptionDate);
    }
}
