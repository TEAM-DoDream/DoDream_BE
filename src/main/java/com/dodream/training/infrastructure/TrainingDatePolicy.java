package com.dodream.training.infrastructure;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TrainingDatePolicy {

    @Value("${work24.bootcamp.month-diff}")
    private static int monthDiff;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String calculateStartDate() {
        return LocalDate.now().plusMonths(monthDiff).format(FORMATTER);
    }

    public static String calculateEndDate() {
        return LocalDate.now().minusMonths(monthDiff).format(FORMATTER);
    }
}
