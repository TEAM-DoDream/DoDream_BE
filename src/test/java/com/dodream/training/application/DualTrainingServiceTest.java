package com.dodream.training.application;

import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("[DualTrainingSerivce] 테스트")
public class DualTrainingServiceTest extends AbstractTrainingServiceTest<DualTrainingService> {

    @Mock
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
