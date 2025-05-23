package com.dodream.common.infrastructure.converter;

import com.dodream.common.dto.response.CommonResponse.ScnItem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;
import java.util.*;

public class ScnListDeserializer extends JsonDeserializer<List<ScnItem>> {

    @Override
    public List<ScnItem> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);
        List<ScnItem> result = new ArrayList<>();

        if(node == null || node.isNull()){
            return result;
        }

        if (node.isArray()) {
            for (JsonNode item : node) {
                if(item != null && !item.isNull()) {
                    result.add(mapper.treeToValue(item, ScnItem.class));
                }
            }
        } else if (node.isObject()) {
            result.add(mapper.treeToValue(node, ScnItem.class));
        }

        return result;
    }
}
