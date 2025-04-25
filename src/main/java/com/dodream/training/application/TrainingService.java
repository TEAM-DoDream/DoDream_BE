package com.dodream.training.application;

import com.dodream.training.dto.response.BootcampDetailApiResponse;
import com.dodream.training.dto.response.BootcampListApiResponse;
import com.dodream.training.infrastructure.mapper.BootcampMapper;
import com.dodream.training.util.executer.BootcampApiExecuter;
import com.dodream.training.util.TrainingCodeResolver;
import com.dodream.training.util.executer.TrainingApiExecuter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TrainingService {

    private final BootcampMapper bootcampMapper;
    private final TrainingCodeResolver trainingCodeResolver;
    private final TrainingApiExecuter trainingApiExecuter;

    public TrainingService(
            BootcampMapper bootcampMapper,
            TrainingCodeResolver trainingCodeResolver,
            @Qualifier("bootcampApiExecuter") TrainingApiExecuter trainingApiExecuter
    ) {
        this.bootcampMapper = bootcampMapper;
        this.trainingCodeResolver = trainingCodeResolver;
        this.trainingApiExecuter = trainingApiExecuter;
    }

    public BootcampListApiResponse getList(
            String pageNum, String regionName, String ncsName
    ) {
        String regionCode = trainingCodeResolver.resolveRegionCode(regionName);
        String ncsCode = trainingCodeResolver.resolveNcsCode(ncsName);

        String result = trainingApiExecuter.callListApi(
                pageNum, regionCode, ncsCode
        );

        return bootcampMapper.jsonStringToBootcampListApiResponse(result);
    }

    public BootcampDetailApiResponse getDetail(
            String srchTrprId, String srchTrprDegr, String srchTorgId
    ){
        String result = trainingApiExecuter.callDetailApi(
                srchTrprId, srchTrprDegr, srchTorgId
        );

        return bootcampMapper.jsonStringToBootcampDetailApiResponse(result);
    }
}
