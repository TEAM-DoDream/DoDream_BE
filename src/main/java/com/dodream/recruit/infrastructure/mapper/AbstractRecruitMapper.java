package com.dodream.recruit.infrastructure.mapper;

import com.dodream.training.exception.TrainingErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractRecruitMapper<T> {
    protected final ObjectMapper objectMapper;

    protected AbstractRecruitMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected <R> R parse(String json, Class<R> clazz) {
        try{
            return objectMapper.readValue(json, clazz);
        }catch(Exception e){
            throw TrainingErrorCode.NOT_CONVERT_JSON_TO_OBJECT.toException();
        }
    }
}
