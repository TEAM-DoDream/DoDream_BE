package com.dodream.todo.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ConvertLocalDateToString {

    public static String calculateTimeAgo(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(createdAt, now);
        if (minutes < 60) {
            return minutes + "분 전";
        }
        long hours = ChronoUnit.HOURS.between(createdAt, now);
        if (hours < 24) {
            return hours + "시간 전";
        }
        long days = ChronoUnit.DAYS.between(createdAt, now);
        return days + "일 전";
    }
}
