package com.dodream.bootcamp.infrastructure;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BootcampDatePolicy {

    private static final int MONTH_DIFF = 2;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static String calculateStartDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -MONTH_DIFF);
        return FORMAT.format(cal.getTime());
    }

    public static String calculateEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, MONTH_DIFF);
        return FORMAT.format(cal.getTime());
    }
}
