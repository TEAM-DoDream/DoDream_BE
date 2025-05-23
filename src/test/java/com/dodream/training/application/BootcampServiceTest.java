package com.dodream.training.application;

import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;

public class BootcampServiceTest extends AbstractTrainingServiceTest<BootcampService>{

    @Mock
    @Qualifier("bootCampApiCaller")
    TrainingApiCaller bootCampApiCaller;

    @Override
    protected BootcampService getService() {
        return new BootcampService(listMapper, detailMapper, trainingCodeResolver, getApiCaller());
    }

    @Override
    protected TrainingApiCaller getApiCaller() {
        return bootCampApiCaller;
    }
}
