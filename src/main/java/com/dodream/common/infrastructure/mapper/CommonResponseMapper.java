package com.dodream.common.infrastructure.mapper;

import com.dodream.common.dto.response.CommonResponse;
import com.dodream.common.exception.CommonErrorCode;
import com.dodream.core.infrastructure.converter.XmlToJsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonResponseMapper {

    private final XmlToJsonConverter xmlToJsonConverter;
    private final ObjectMapper objectMapper;

    public CommonResponse toRegionResponse(String xml) {
        String json = xmlToJsonConverter.convertXmlToJson(xml);
        try {
            return objectMapper.readValue(json, CommonResponse.class);
        } catch (JsonProcessingException e) {
            throw CommonErrorCode.JSON_TO_OBJECT_CONVERT_ERROR.toException();
        }
    }
}
