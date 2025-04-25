package com.dodream.training.application;

import com.dodream.training.dto.response.BootcampDetailApiResponse;
import com.dodream.training.dto.response.BootcampListApiResponse;
import com.dodream.training.infrastructure.TrainingApiCaller;
import com.dodream.training.infrastructure.TrainingDatePolicy;
import com.dodream.training.infrastructure.mapper.BootcampMapper;
import com.dodream.training.util.TrainingCodeResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class TrainingService {

    private final TrainingApiCaller trainingApiCaller;
    private final BootcampMapper bootcampMapper;
    private final TrainingCodeResolver trainingCodeResolver;

    public BootcampListApiResponse getList(
            String pageNum, String regionName, String ncsName
    ) {
        String startDate = TrainingDatePolicy.calculateStartDate();
        String endDate = TrainingDatePolicy.calculateEndDate();

        String regionCode = trainingCodeResolver.resolveRegionCode(regionName);
        String ncsCode = trainingCodeResolver.resolveNcsCode(ncsName);

        String result = trainingApiCaller.bootcampListApiCaller(
                pageNum, regionCode, ncsCode, startDate, endDate
        );

        return bootcampMapper.jsonStringToBootcampListApiResponse(result);
    }

    public BootcampDetailApiResponse getDetail(
            String srchTrprId, String srchTrprDegr, String srchTorgId
    ){
        String result = trainingApiCaller.bootcampDetailApiCaller(
                srchTrprId, srchTrprDegr, srchTorgId
        );

        return bootcampMapper.jsonStringToBootcampDetailApiResponse(result);
    }
}
