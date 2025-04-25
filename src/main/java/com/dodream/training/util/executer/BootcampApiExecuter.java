package com.dodream.training.util.executer;

import com.dodream.training.infrastructure.TrainingDatePolicy;
import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

@RequiredArgsConstructor
public class BootcampApiExecuter implements TrainingApiExecuter {

    @Qualifier("bootCampApiCaller")
    private final TrainingApiCaller trainingApiCaller;

    @Override
    public String callListApi(
            String pageNum, String regionCode, String ncsCode
    ) {
        String startDate = TrainingDatePolicy.calculateStartDate();
        String endDate = TrainingDatePolicy.calculateEndDate();

        return trainingApiCaller.getListApi(
                pageNum, regionCode, ncsCode, startDate, endDate
        );
    }

    @Override
    public String callDetailApi(
            String trprId, String trprDegr, String torgId
    ) {
        return trainingApiCaller.getDetailApi(
                trprId, trprDegr, torgId
        );
    }
}

