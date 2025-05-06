package com.dodream.training.infrastructure.mapper;

import com.dodream.core.exception.GlobalErrorCode;
import com.dodream.training.exception.TrainingErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractTrainingMapper<L,D> implements TrainingMapper<L,D> {

    protected final ObjectMapper objectMapper;

    protected AbstractTrainingMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected <R> R parse(String json, Class<R> clazz) {
        try{
            return objectMapper.readValue(json, clazz);
        }catch(Exception e){
            throw GlobalErrorCode.CANNOT_CONVERT_OBJECT.toException();
        }
    }
}
