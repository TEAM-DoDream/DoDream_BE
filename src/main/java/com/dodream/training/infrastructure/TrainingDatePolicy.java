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

    public String calculateStartDate() {
        return LocalDate.now().minusMonths(monthDiff).format(FORMATTER);
    }

    public String calculateEndDate() {
        return LocalDate.now().plusMonths(monthDiff).format(FORMATTER);
    }
}
