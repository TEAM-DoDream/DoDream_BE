package com.dodream.training.util;

public class TrainingUrlUtils {

    private static String BASE_URL = "https://hrd.work24.go.kr/hrdp/co/pcobo/PCOBO0100P.do?";

    private static StringBuilder sb = new StringBuilder();

    public static String generateTrainingUrl(String trprId, String trprDeg, String torgId){
        return sb.append(BASE_URL)
                .append("tracseId=").append(trprId)
                .append("&tracseTme=").append(trprDeg)
                .append("&trainstCstmrId=").append(torgId)
                .toString();
    }
}
