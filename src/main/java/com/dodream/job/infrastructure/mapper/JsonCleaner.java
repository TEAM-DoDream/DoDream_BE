package com.dodream.job.infrastructure.mapper;

import com.dodream.job.exception.JobErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonCleaner {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T cleanAndParse(String rawOutput, Class<T> valueType){
        // 1. ```json 또는 ``` 제거
        String cleaned = rawOutput.replaceAll("(?m)^```json|^```", "").trim();

        // 2. 한 줄 주석 제거 (// ...)
        cleaned = cleaned.replaceAll("(?m)//.*?$", "");

        // 3. 블록 주석 제거 (/* ... */)
        cleaned = cleaned.replaceAll("/\\*(?s).*?\\*/", "");

        // 4. JSON 파싱
        try{
            return objectMapper.readValue(cleaned, valueType);
        }catch (Exception e){
            throw JobErrorCode.CANNOT_CONVERT_CLOVA_RESPONSE.toException();
        }
    }
}
