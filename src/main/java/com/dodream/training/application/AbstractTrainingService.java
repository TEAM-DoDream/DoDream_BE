package com.dodream.training.application;

import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import com.dodream.training.presentation.value.SortBy;
import com.dodream.training.util.TrainingCodeResolver;

import java.util.List;

public abstract class AbstractTrainingService {

    protected final TrainingMapper<TrainingListApiResponse> listMapper;
    protected final TrainingMapper<TrainingDetailApiResponse> detailMapper;
    protected final TrainingCodeResolver trainingCodeResolver;

    protected AbstractTrainingService(
            TrainingMapper<TrainingListApiResponse> listMapper,
            TrainingMapper<TrainingDetailApiResponse> detailMapper,
            TrainingCodeResolver trainingCodeResolver
    ) {
        this.listMapper = listMapper;
        this.detailMapper = detailMapper;
        this.trainingCodeResolver = trainingCodeResolver;
    }

    protected abstract TrainingApiCaller getApiCaller();

    public TrainingListApiResponse getList(String pageNum, String regionName, String jobName, SortBy sortBy) {
        String regionCode = trainingCodeResolver.resolveRegionCode(regionName);
        String ncsCode = trainingCodeResolver.resolveNcsCode(jobName);

        String result = getApiCaller().getListApi(pageNum, regionCode, ncsCode, sortBy);

        TrainingListApiResponse response = listMapper.jsonToResponseDto(result);
        List<TrainingListApiResponse.BootcampItem> mappedResult = response.srchList().stream()
                .map(TrainingListApiResponse.BootcampItem::from)
                .toList();

        return new TrainingListApiResponse(response.scnCnt(), response.pageNum(), response.pageSize(), mappedResult);
    }

    public TrainingDetailApiResponse getDetail(String srchTrprId, String srchTrprDegr, String srchTorgId) {
        String result = getApiCaller().getDetailApi(srchTrprId, srchTrprDegr, srchTorgId);
        return detailMapper.jsonToResponseDto(result);
    }


}
