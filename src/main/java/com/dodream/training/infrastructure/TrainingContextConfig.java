package com.dodream.training.infrastructure;

import com.dodream.training.infrastructure.caller.BootCampApiCaller;
import com.dodream.training.infrastructure.caller.DualTrainingApiCaller;
import com.dodream.training.util.executer.BootcampApiExecuter;
import com.dodream.training.util.executer.DualTrainingApiExecuter;
import com.dodream.training.util.executer.TrainingApiExecuter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TrainingContextConfig {

    private final BootCampApiCaller bootCampApiCaller;
    private final DualTrainingApiCaller dualTrainingApiCaller;
    private final TrainingDatePolicy trainingDatePolicy;

    @Bean
    public TrainingApiExecuter bootcampApiExecuter() {
        return new BootcampApiExecuter(bootCampApiCaller, trainingDatePolicy);
    }

    @Bean
    public TrainingApiExecuter dualTrainingApiExecuter() {
        return new DualTrainingApiExecuter(dualTrainingApiCaller, trainingDatePolicy);
    }
}
