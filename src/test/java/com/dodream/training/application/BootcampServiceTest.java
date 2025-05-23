package com.dodream.training.application;

import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("[BootcampService] 테스트")
public class BootcampServiceTest extends AbstractTrainingServiceTest<BootcampService>{

    @Mock
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
