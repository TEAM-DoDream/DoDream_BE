package com.dodream.core.infrastructure.mapper;

import com.dodream.training.exception.TrainingErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractObjectMapper<T>{

    protected final ObjectMapper objectMapper;

    public AbstractObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <R> R parse(String json, Class<R> clazz) {
        try{
            return objectMapper.readValue(json, clazz);
        }catch(Exception e){
            throw TrainingErrorCode.NOT_CONVERT_JSON_TO_OBJECT.toException();
        }
    }
}
