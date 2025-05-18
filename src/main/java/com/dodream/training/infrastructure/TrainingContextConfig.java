package com.dodream.training.infrastructure;

import com.dodream.training.infrastructure.caller.BootCampApiCaller;
import com.dodream.training.infrastructure.caller.DualTrainingApiCaller;
import com.dodream.training.infrastructure.mapper.TrainingDetailResponseDtoMapper;
import com.dodream.training.infrastructure.mapper.TrainingListApiReseponseMapper;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import com.dodream.training.util.executer.BootcampApiExecuter;
import com.dodream.training.util.executer.DualTrainingApiExecuter;
import com.dodream.training.util.executer.TrainingApiExecuter;
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
    public TrainingApiExecuter bootcampApiExecuter() {
        return new BootcampApiExecuter(bootCampApiCaller);
    }

    @Bean
    public TrainingApiExecuter dualTrainingApiExecuter() {
        return new DualTrainingApiExecuter(dualTrainingApiCaller);
    }

    @Bean
    public TrainingMapper trainingListApiReseponseMapper(){
        return new TrainingListApiReseponseMapper(objectMapper);
    }

    @Bean
    public TrainingMapper trainingDetailResponseDtoMapper(){
        return new TrainingDetailResponseDtoMapper(objectMapper);
    }
}
