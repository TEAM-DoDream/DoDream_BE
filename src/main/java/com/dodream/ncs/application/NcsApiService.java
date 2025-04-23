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

    public CommonResponse.SrchList getNcsInfo(String ncsClassCode){
        String xml = commonApiCaller.callCommonApi(ncsClassCode, null, null);
        return commonResponseMapper.toCommonResponse(xml).srchList();
    }

    public CommonResponse.SrchList getLargeNcsInfo(){
        return getNcsInfo("05");
    }

    public CommonResponse.SrchList getMiddleNcsInfo(){
        return getNcsInfo("06");
    }

    public CommonResponse.SrchList getSmallNcsInfo(){
        return getNcsInfo("07");
    }

    public CommonResponse.SrchList getSmallestNcsInfo(){
        return getNcsInfo("08");
    }
}
