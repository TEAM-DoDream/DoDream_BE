package com.dodream.training.util;

import com.dodream.training.infrastructure.TrainingApiCaller;
import com.dodream.training.infrastructure.TrainingDatePolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingApiExecuter {

    private final TrainingApiCaller trainingApiCaller;

    public String callBootcampListApi(
            String pageNum, String regionCode, String ncsCode
    ) {
        String startDate = TrainingDatePolicy.calculateStartDate();
        String endDate = TrainingDatePolicy.calculateEndDate();

        return trainingApiCaller.bootcampListApiCaller(
                pageNum, regionCode, ncsCode, startDate, endDate
        );
    }

    public String callBootcampDetailApi(
            String trprId, String trprDegr, String torgId
    ) {
        return trainingApiCaller.bootcampDetailApiCaller(
                trprId, trprDegr, torgId
        );
    }
}

