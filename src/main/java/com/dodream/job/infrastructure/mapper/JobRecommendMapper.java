package com.dodream.job.infrastructure.mapper;

import com.dodream.core.infrastructure.mapper.AbstractObjectMapper;
import com.dodream.job.dto.response.JobRecommendationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JobRecommendMapper extends AbstractObjectMapper<JobRecommendationResponse> {

    public JobRecommendMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public JobRecommendationResponse parse(String json) {
        return parse(json, JobRecommendationResponse.class);
    }
}
