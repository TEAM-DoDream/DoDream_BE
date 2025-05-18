package com.dodream.training.application;

import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import com.dodream.training.util.TrainingCodeResolver;
import com.dodream.training.util.executer.TrainingApiExecuter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
public class BootcampService{

    private final TrainingMapper<TrainingListApiResponse> listMapper;
    private final TrainingMapper<TrainingDetailApiResponse> detailMapper;
    private final TrainingCodeResolver trainingCodeResolver;
    private final TrainingApiExecuter trainingApiExecuter;

    public BootcampService(
            @Qualifier("trainingListApiReseponseMapper") TrainingMapper<TrainingListApiResponse> listMapper,
            @Qualifier("trainingDetailResponseDtoMapper") TrainingMapper<TrainingDetailApiResponse> detailMapper,
            TrainingCodeResolver trainingCodeResolver,
            @Qualifier("bootcampApiExecuter") TrainingApiExecuter trainingApiExecuter
    ) {
        this.listMapper = listMapper;
        this.detailMapper = detailMapper;
        this.trainingCodeResolver = trainingCodeResolver;
        this.trainingApiExecuter = trainingApiExecuter;
    }

    public TrainingListApiResponse getList(
            String pageNum, String regionName, String jobName, LocalDate startDate, LocalDate endDate
    ) {
        String regionCode = trainingCodeResolver.resolveRegionCode(regionName);
        String ncsCode = trainingCodeResolver.resolveNcsCode(jobName);

        String result = trainingApiExecuter.callListApi(
                pageNum, regionCode, ncsCode, startDate, endDate
        );

        TrainingListApiResponse trainingListApiResponse = listMapper.jsonToResponseDto(result);

        List<TrainingListApiResponse.BootcampItem> updatedList = trainingListApiResponse.srchList().stream()
                .map(item -> {
                    int trainingTime = getDetail(item.trprId(), item.trprDegr(), item.trainstCstId())
                            .instBaseInfo().trtm();

                    return TrainingListApiResponse.BootcampItem.from(item, trainingTime);
                })
                .toList();

        return new TrainingListApiResponse(
                trainingListApiResponse.scnCnt(),
                trainingListApiResponse.pageNum(),
                trainingListApiResponse.pageSize(),
                updatedList
        );
    }

    public TrainingDetailApiResponse getDetail(
            String srchTrprId, String srchTrprDegr, String srchTorgId
    ){
        String result = trainingApiExecuter.callDetailApi(
                srchTrprId, srchTrprDegr, srchTorgId
        );

        return detailMapper.jsonToResponseDto(result);
    }
}
