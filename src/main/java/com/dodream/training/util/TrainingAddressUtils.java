package com.dodream.training.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrainingAddressUtils {

    public static String extractMainRegion(String fullAddress) {
        if (fullAddress == null) return "";
        // "시/도 시/군/구 (선택적으로 하나 더)"
        Pattern pattern = Pattern.compile("^([^\\s]+도|[^\\s]+시)\\s+([^\\s]+시|[^\\s]+군|[^\\s]+구)(\\s+[^\\s]+구)?");
        Matcher matcher = pattern.matcher(fullAddress);
        if (matcher.find()) {
            return matcher.group().trim();
        }
        return "";
    }
}
