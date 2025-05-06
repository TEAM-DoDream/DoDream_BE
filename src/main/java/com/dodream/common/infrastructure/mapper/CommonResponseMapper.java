package com.dodream.common.infrastructure.mapper;

import com.dodream.common.dto.response.CommonResponse;
import com.dodream.common.exception.CommonErrorCode;
import com.dodream.core.infrastructure.converter.XmlToJsonConverter;
import com.dodream.core.infrastructure.mapper.AbstractObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class CommonResponseMapper extends AbstractObjectMapper<CommonResponse> {

    private final XmlToJsonConverter xmlToJsonConverter;

    public CommonResponseMapper(ObjectMapper objectMapper, XmlToJsonConverter xmlToJsonConverter) {
        super(objectMapper);
        this.xmlToJsonConverter = xmlToJsonConverter;
    }

    public CommonResponse toCommonResponse(String xml) {
        String json = xmlToJsonConverter.convertXmlToJson(xml);
        return parse(json, CommonResponse.class);
    }
}
