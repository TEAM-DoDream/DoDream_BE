package com.dodream.training.application;

<<<<<<< HEAD
import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import com.dodream.training.util.TrainingCodeResolver;
import com.dodream.training.util.executer.TrainingApiExecuter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
=======
import lombok.extern.log4j.Log4j2;
>>>>>>> 4d30e0d (feat: 일학습병행 훈련과정 인프라 로직 작성 및 인터페이스로 책임 분리)
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class DualTrainingService {
<<<<<<< HEAD
    private final TrainingMapper trainingMapper;
    private final TrainingCodeResolver trainingCodeResolver;
    private final TrainingApiExecuter trainingApiExecuter;

    public DualTrainingService(
            TrainingMapper trainingMapper,
            TrainingCodeResolver trainingCodeResolver,
            @Qualifier("dualTrainingApiExecuter") TrainingApiExecuter trainingApiExecuter
    ){
        this.trainingMapper = trainingMapper;
        this.trainingCodeResolver = trainingCodeResolver;
        this.trainingApiExecuter = trainingApiExecuter;
    }

    public TrainingListApiResponse getList(
            String pageNum, String regionName, String ncsName
    ) {
        String regionCode = trainingCodeResolver.resolveRegionCode(regionName);
        String ncsCode = trainingCodeResolver.resolveNcsCode(ncsName);

        String result = trainingApiExecuter.callListApi(
                pageNum, regionCode, ncsCode
        );

        return trainingMapper.jsonStringToBootcampListApiResponse(result);
    }


    public TrainingDetailApiResponse getDetail(String srchTrprId, String srchTrprDegr, String srchTorgId) {
        String result = trainingApiExecuter.callDetailApi(
                srchTrprId, srchTrprDegr, srchTorgId
        );
        return trainingMapper.jsonStringToBootcampDetailApiResponse(result);
    }
=======

>>>>>>> 4d30e0d (feat: 일학습병행 훈련과정 인프라 로직 작성 및 인터페이스로 책임 분리)
}
