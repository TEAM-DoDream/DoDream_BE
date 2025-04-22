package com.dodream.region.application;

import com.dodream.common.dto.response.CommonResponse;
import com.dodream.common.infrastructure.CommonApiCaller;
import com.dodream.common.infrastructure.mapper.CommonResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegionApiService {

    private final CommonApiCaller commonApiCaller;
    private final CommonResponseMapper commonResponseMapper;

    public CommonResponse.SrchList getLargeRegionCode() {
        String xml = commonApiCaller.callCommonApi("00", null, null);
        return commonResponseMapper.toRegionResponse(xml).srchList();
    }

    public List<CommonResponse.ScnItem> getMiddleRegion() {
        List<CommonResponse.ScnItem> largeRegions = getLargeRegionCode().scnList();
        Map<String, String> largeCodeToName = largeRegions.stream()
                .filter(r -> r.useYn().equals("Y"))
                .collect(Collectors.toMap(CommonResponse.ScnItem::rsltCode, CommonResponse.ScnItem::rsltName));

        List<CommonResponse.ScnItem> result = new ArrayList<>();

        for (String largeCode : largeCodeToName.keySet()) {
            String xml = commonApiCaller.callCommonApi("01", largeCode, null);
            CommonResponse response = commonResponseMapper.toRegionResponse(xml);

            for (CommonResponse.ScnItem item : response.srchList().scnList()) {
                if (item.useYn().equals("N")) continue;

                result.add(new CommonResponse.ScnItem(
                        item.rsltCode(),
                        largeCodeToName.get(largeCode) + " " + item.rsltName().trim(),
                        item.useYn()
                ));
            }
        }

        return result;
    }
}
