package com.dodream.training.infrastructure;

import com.dodream.training.infrastructure.caller.BootCampApiCaller;
import com.dodream.training.infrastructure.caller.DualTrainingApiCaller;
import com.dodream.training.infrastructure.mapper.TrainingDetailResponseDtoMapper;
import com.dodream.training.infrastructure.mapper.TrainingListApiReseponseMapper;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TrainingContextConfig {

    private final BootCampApiCaller bootCampApiCaller;
    private final DualTrainingApiCaller dualTrainingApiCaller;
    private final ObjectMapper objectMapper;

    @Bean
    public TrainingMapper trainingListApiReseponseMapper(){
        return new TrainingListApiReseponseMapper(objectMapper);
    }

    @Bean
    public TrainingMapper trainingDetailResponseDtoMapper(){
        return new TrainingDetailResponseDtoMapper(objectMapper);
    }
}
