package com.dodream.job.infrastructure.mapper;

public class JsonCleaner {

    public static String cleanJson(String rawOutput){
        // 1. ```json 또는 ``` 제거
        String cleaned = rawOutput.replaceAll("(?m)^```json|^```", "").trim();

        // 2. 한 줄 주석 제거 (// ...)
        cleaned = cleaned.replaceAll("(?m)//.*?$", "");

        // 3. 블록 주석 제거 (/* ... */)
        cleaned = cleaned.replaceAll("/\\*(?s).*?\\*/", "");

        return cleaned;
    }
}
