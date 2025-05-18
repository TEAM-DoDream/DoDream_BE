package com.dodream.training.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class TrainingDateUtils {

    private static final DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static String calculateDuration(String start, String end){
        LocalDate startDate = LocalDate.parse(start, INPUT_DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, INPUT_DATE_FORMATTER);

        Period period = Period.between(startDate, endDate);
        int months = period.getMonths();
        int days = period.getDays();

        int roundedMonths = months;
        if (months == 0 && days < 30) {
            return days + "일";
        }

        // 15일 이상이면 올림
        if (days >= 15) {
            roundedMonths += 1;
        }

        return "약 " + roundedMonths + "개월";
    }

    public static String convertDateFormat(String inputDate){
        LocalDate date = LocalDate.parse(inputDate, INPUT_DATE_FORMATTER);
        return date.format(OUTPUT_DATE_FORMATTER);
    }
}
