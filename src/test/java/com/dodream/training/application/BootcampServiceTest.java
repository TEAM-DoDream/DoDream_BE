package com.dodream.training.application;

import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import com.dodream.training.util.TrainingCodeResolver;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;

public class BootcampServiceTest {

    @Mock
    TrainingMapper<TrainingListApiResponse> listMapper;

    @Mock
    TrainingMapper<TrainingDetailApiResponse> detailMapper;

    @Mock
    TrainingCodeResolver trainingCodeResolver;

    @Mock
    @Qualifier("bootCampApiCaller")
    TrainingApiCaller bootcampApiCaller;

    BootcampService bootcampService;

    @BeforeEach
    void setUp(){
        bootcampService = new BootcampService(
                listMapper,
                detailMapper,
                trainingCodeResolver,
                bootcampApiCaller
        );
    }
}
