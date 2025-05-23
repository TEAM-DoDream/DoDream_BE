package com.dodream.training.application;

import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;

public class DualTrainingServiceTest extends AbstractTrainingServiceTest<DualTrainingService> {

    @Mock
    @Qualifier("dualTrainingApiCaller")
    TrainingApiCaller dualTrainingApiCaller;

    @Override
    protected DualTrainingService getService() {
        return new DualTrainingService(listMapper, detailMapper, trainingCodeResolver, getApiCaller());
    }

    @Override
    protected TrainingApiCaller getApiCaller() {
        return dualTrainingApiCaller;
    }
}
