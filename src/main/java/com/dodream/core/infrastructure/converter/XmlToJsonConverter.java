package com.dodream.core.infrastructure.converter;

import com.dodream.core.exception.GlobalErrorCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

@Component
public class XmlToJsonConverter {
    public String convertXmlToJson(String xml) {
        try {
            ObjectMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(xml.getBytes());
            ObjectMapper jsonMapper = new ObjectMapper();
            return jsonMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            throw GlobalErrorCode.XML_TO_JSON_CONVERT_ERROR.toException();
        }
    }
}
