package com.dodream.recruit.infrastructure.mapper;

import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.dodream.recruit.exception.RecruitErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public abstract class AbstractRecruitMapper<T> {
    protected final ObjectMapper objectMapper;

    protected AbstractRecruitMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected <R> R parse(String json, Class<R> clazz) {
        try{
            return objectMapper.readValue(json, clazz);
        }catch(Exception e){
            throw RecruitErrorCode.CANNOT_CONVERT_JSON.toException();
        }
    }
}
