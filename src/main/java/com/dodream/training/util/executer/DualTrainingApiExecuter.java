package com.dodream.training.util.executer;

import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;


@RequiredArgsConstructor
public class DualTrainingApiExecuter implements TrainingApiExecuter {

    @Qualifier("dualTrainingApiCaller")
    private final TrainingApiCaller trainingApiCaller;

    @Override
    public String callListApi(String pageNum, String regionCode, String ncsCode) {
        return trainingApiCaller.getListApi(
                pageNum, regionCode, ncsCode
        );
    }

    @Override
    public String callDetailApi(String trprId, String trprDegr, String torgId) {
        return trainingApiCaller.getDetailApi(
                trprId, trprDegr, torgId
        );
    }
}
