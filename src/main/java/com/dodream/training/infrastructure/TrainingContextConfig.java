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
<<<<<<< HEAD
    private final TrainingDatePolicy trainingDatePolicy;
    @Bean
    public TrainingApiExecuter bootcampApiExecuter() {
        return new BootcampApiExecuter(bootCampApiCaller, trainingDatePolicy);
=======

    @Bean
    public TrainingApiExecuter bootcampApiExecuter() {
        return new BootcampApiExecuter(bootCampApiCaller);
>>>>>>> 4d30e0d (feat: 일학습병행 훈련과정 인프라 로직 작성 및 인터페이스로 책임 분리)
    }

    @Bean
    public TrainingApiExecuter dualTrainingApiExecuter() {
<<<<<<< HEAD
        return new DualTrainingApiExecuter(dualTrainingApiCaller, trainingDatePolicy);
=======
        return new DualTrainingApiExecuter(dualTrainingApiCaller);
>>>>>>> 4d30e0d (feat: 일학습병행 훈련과정 인프라 로직 작성 및 인터페이스로 책임 분리)
    }

}
