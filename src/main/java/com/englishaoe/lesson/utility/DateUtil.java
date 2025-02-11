package com.englishaoe.lesson.utility;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static String getCurrentDate() {
        LocalDate localDate = LocalDate.now();
        return formatter.format(localDate);
    }
    public static Boolean isDateExpired(String date) {
        try {
            LocalDate expireDate = LocalDate.parse(date, formatter);
            LocalDate currentDate = LocalDate.now();
            return expireDate.isAfter(currentDate);
        } catch (DateTimeParseException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public static String getExpireDate(int months){
        LocalDate localDate = LocalDate.now();
        LocalDate expireSubscriptionDate = localDate.plusDays(months* 30L);
        return formatter.format(expireSubscriptionDate);
    }
    /**
     * function helps determine if user already has an active subscription, then
     * this function extend period which was bought plus what remains
     * exapmle: expire = 25.03.2025, previous = 25.01.2025, today = 20.01.2025
     * output: 30.03.2025 (just add 5 days to actually expire date)
     * */
    public static String extendSubscriptionPeriod(String expireDateStr, String previousExpireDateStr ){
        if (previousExpireDateStr == null || previousExpireDateStr.isBlank()) return expireDateStr;
        LocalDate expireDate = LocalDate.parse(expireDateStr, formatter);
        LocalDate previousExpireDate = LocalDate.parse(previousExpireDateStr, formatter);
        LocalDate todayDate = LocalDate.now();
        long remainingDays = ChronoUnit.DAYS.between(todayDate, previousExpireDate);
        if (remainingDays < 0) return expireDateStr;

        return formatter.format(expireDate.plusDays(remainingDays));
    }
}
