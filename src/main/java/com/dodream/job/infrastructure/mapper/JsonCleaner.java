package com.dodream.job.infrastructure.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonCleaner {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T cleanAndParse(String rawOutput, Class<T> valueType) throws Exception {
        // 1. ```json 또는 ``` 제거
        String cleaned = rawOutput.replaceAll("(?m)^```json|^```", "").trim();

        // 2. 한 줄 주석 제거 (// ...)
        cleaned = cleaned.replaceAll("(?m)//.*?$", "");

        // 3. 블록 주석 제거 (/* ... */)
        cleaned = cleaned.replaceAll("/\\*.*?\\*/", "");

        // 4. JSON 파싱
        return objectMapper.readValue(cleaned, valueType);
    }
}
