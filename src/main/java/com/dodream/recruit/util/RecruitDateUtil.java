package com.dodream.recruit.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class RecruitDateUtil {

    private static final DateTimeFormatter INPUT_DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssZ");
    private static final DateTimeFormatter OUTPUT_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd(E)", Locale.KOREAN);

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    public static String getRemainingDate(String date, String closeType){
        String defaultMessage = getCloseTypeMessage(closeType);
        if (!"1".equals(closeType)) {
            return defaultMessage;
        }

        ZonedDateTime expirationZdt = ZonedDateTime.parse(date, INPUT_DATE_TIME_FORMATTER);
        LocalDate expirationLocalDate = expirationZdt.toLocalDate();
        LocalDate todayKst = LocalDate.now(ZONE_ID);

        long daysBetween = ChronoUnit.DAYS.between(todayKst, expirationLocalDate);

        if (daysBetween < 0) {
            return "마감됨";
        } else if (daysBetween == 0) {
            return "D-day";
        } else {
            return "D-" + daysBetween;
        }
    }

    public static String getExpirationDate(String date, String closeType){
        String defaultMessage = getCloseTypeMessage(closeType);
        if (!"1".equals(closeType)) {
            return defaultMessage;
        }

        return getFormattedDate(date);
    }

    public static String getFormattedDate(String date){
        String trimmedDateString = date.trim();

        ZonedDateTime expirationZdt = ZonedDateTime.parse(trimmedDateString, INPUT_DATE_TIME_FORMATTER);
        LocalDate localDate = expirationZdt.toLocalDate();

        return localDate.format(OUTPUT_DATE_FORMATTER);
    }

    public static String getFormattedPostingDate(String date){
        ZonedDateTime inputTime = ZonedDateTime.parse(date, INPUT_DATE_TIME_FORMATTER);

        ZonedDateTime now = ZonedDateTime.now();

        long days = ChronoUnit.DAYS.between(inputTime, now);
        if (days > 0) return days + "일 전";

        long hours = ChronoUnit.HOURS.between(inputTime, now);
        if (hours > 0) return hours + "시간 전";

        long minutes = ChronoUnit.MINUTES.between(inputTime, now);
        if (minutes > 0) return minutes + "분 전";

        return "방금 전";
    }

    public static String toUnixTime(String date) {
        if (date == null || date.isEmpty()) return null;
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return String.valueOf(localDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toEpochSecond());
    }

    private static String getCloseTypeMessage(String closeType) {
        return switch (closeType) {
            case "2" -> "채용시 마감";
            case "3" -> "상시 채용";
            case "4" -> "수시 채용";
            default -> closeType.equals("1") ? null : "일자 변환 오류";
        };
    }
}
