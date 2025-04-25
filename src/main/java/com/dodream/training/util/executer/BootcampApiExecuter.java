package com.dodream.training.util.executer;

import com.dodream.training.infrastructure.TrainingDatePolicy;
import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

@RequiredArgsConstructor
public class BootcampApiExecuter implements TrainingApiExecuter {

    @Qualifier("bootCampApiCaller")
    private final TrainingApiCaller trainingApiCaller;

<<<<<<< HEAD
<<<<<<< HEAD
    private final TrainingDatePolicy trainingDatePolicy;

=======
>>>>>>> 4d30e0d (feat: 일학습병행 훈련과정 인프라 로직 작성 및 인터페이스로 책임 분리)
=======
    private final TrainingDatePolicy trainingDatePolicy;

>>>>>>> 8b81609 (feat: 일학습병행관련 컨트롤러 및 서비스 로직 구현 완료)
    @Override
    public String callListApi(
            String pageNum, String regionCode, String ncsCode
    ) {
<<<<<<< HEAD
<<<<<<< HEAD
        String startDate = trainingDatePolicy.calculateStartDate();
        String endDate = trainingDatePolicy.calculateEndDate();
=======
        String startDate = TrainingDatePolicy.calculateStartDate();
        String endDate = TrainingDatePolicy.calculateEndDate();
>>>>>>> 4d30e0d (feat: 일학습병행 훈련과정 인프라 로직 작성 및 인터페이스로 책임 분리)
=======
        String startDate = trainingDatePolicy.calculateStartDate();
        String endDate = trainingDatePolicy.calculateEndDate();
>>>>>>> 8b81609 (feat: 일학습병행관련 컨트롤러 및 서비스 로직 구현 완료)

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

