package com.dodream.training.util;

public class TrainingUrlUtils {

    private static final String BASE_URL = "https://hrd.work24.go.kr/hrdp/co/pcobo/PCOBO0100P.do?";

    public static String generateTrainingUrl(String trprId, String trprDeg, String torgId){
        return BASE_URL +
                "tracseId=" + trprId +
                "&tracseTme=" + trprDeg +
                "&trainstCstmrId=" + torgId;
    }
}
