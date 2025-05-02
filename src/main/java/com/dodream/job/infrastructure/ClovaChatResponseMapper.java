package com.dodream.job.infrastructure;

import com.dodream.job.dto.response.ChatResponse;
import com.dodream.job.exception.JobErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClovaChatResponseMapper {

    private final ObjectMapper objectMapper;

    public ChatResponse jsonToChatResponse(String json) {
        try{
            return objectMapper.readValue(json, ChatResponse.class);
        } catch (Exception e){
            throw JobErrorCode.CANNOT_CONVERT_OBJECT.toException();
        }
    }
}
