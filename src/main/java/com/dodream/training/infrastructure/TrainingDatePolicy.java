package com.dodream.training.infrastructure;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Log4j2
public class TrainingDatePolicy {

    @Value("${work24.month-diff}")
    private int monthDiff;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public String calculateStartDate(LocalDate startDate) {
        if(startDate == null) {
            return LocalDate.now().format(FORMATTER);
        }
        return startDate.format(FORMATTER);
    }

    public String calculateEndDate(LocalDate endDate) {
        if(endDate == null) {
            return LocalDate.now().plusMonths(monthDiff).format(FORMATTER);
        }
        return endDate.format(FORMATTER);
    }
}
