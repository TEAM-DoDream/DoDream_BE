package com.dodream.training.application;

import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import com.dodream.training.util.TrainingCodeResolver;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class BootcampService extends AbstractTrainingService{

    private final TrainingApiCaller trainingApiCaller;

    public BootcampService(
            @Qualifier("trainingListApiReseponseMapper") TrainingMapper<TrainingListApiResponse> listMapper,
            @Qualifier("trainingDetailResponseDtoMapper") TrainingMapper<TrainingDetailApiResponse> detailMapper,
            TrainingCodeResolver trainingCodeResolver,
            @Qualifier("bootCampApiCaller") TrainingApiCaller trainingApiCaller
    ) {
        super(listMapper, detailMapper, trainingCodeResolver);
        this.trainingApiCaller = trainingApiCaller;
    }

    @Override
    protected TrainingApiCaller getApiCaller() {
        return trainingApiCaller;
    }
}
