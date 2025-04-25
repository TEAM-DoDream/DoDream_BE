package com.dodream.training.infrastructure;

import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrainingDatePolicy {

    @Value("${work24.bootcamp.month-diff}")
    private static int monthDiff;

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static String calculateStartDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -monthDiff);
        return FORMAT.format(cal.getTime());
    }

    public static String calculateEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, monthDiff);
        return FORMAT.format(cal.getTime());
    }
}
