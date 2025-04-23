package com.dodream.ncs.application;

import com.dodream.common.dto.response.CommonResponse;
import com.dodream.common.infrastructure.CommonApiCaller;
import com.dodream.common.infrastructure.mapper.CommonResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NcsApiService {

    private final CommonApiCaller commonApiCaller;
    private final CommonResponseMapper commonResponseMapper;

    public CommonResponse.SrchList getLargeNcsInfo(){
        String xml = commonApiCaller.callCommonApi("05", null, null);
        return commonResponseMapper.toRegionResponse(xml).srchList();
    }

    public CommonResponse.SrchList getMiddleNcsInfo(){
        String xml = commonApiCaller.callCommonApi("06", null, null);
        return commonResponseMapper.toRegionResponse(xml).srchList();
    }

    public CommonResponse.SrchList getSmallNcsInfo(){
        String xml = commonApiCaller.callCommonApi("07", null, null);
        return commonResponseMapper.toRegionResponse(xml).srchList();
    }

    public CommonResponse.SrchList getSmallestNcsInfo(){
        String xml = commonApiCaller.callCommonApi("08", null, null);
        return commonResponseMapper.toRegionResponse(xml).srchList();
    }
}
