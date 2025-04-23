package com.dodream.region;

import com.dodream.common.dto.response.CommonResponse;
import com.dodream.common.infrastructure.CommonApiCaller;
import com.dodream.common.infrastructure.mapper.CommonResponseMapper;
import com.dodream.region.application.RegionApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("[RegionApiServiceTest] - 테스트")
public class RegionApiServiceTest {

    @Mock
    private CommonApiCaller commonApiCaller;

    @Mock
    private CommonResponseMapper commonResponseMapper;

    @InjectMocks
    private RegionApiService regionApiService;

    @Test
    @DisplayName("[getLargeRegionCode] - 정상 작동 테스트")
    void getLargeRegionCodeTest() {
        // given
        String xml = "<xml>large-region</xml>";

        CommonResponse.ScnItem item1 = new CommonResponse.ScnItem("11", "서울", "Y");
        CommonResponse.ScnItem item2 = new CommonResponse.ScnItem("26", "부산", "Y");
        CommonResponse.SrchList srchList = new CommonResponse.SrchList(List.of(item1, item2));
        CommonResponse response = new CommonResponse("코드이름", "개수", srchList);

        when(commonApiCaller.callCommonApi("00", null, null)).thenReturn(xml);
        when(commonResponseMapper.toRegionResponse(xml)).thenReturn(response);

        // when
        CommonResponse.SrchList result = regionApiService.getLargeRegionCode();

        // then
        assertEquals(2, result.scnList().size());
        assertEquals("서울", result.scnList().get(0).rsltName());
    }

    @Test
    @DisplayName("[getMiddleRegion] - 정상 작동 테스트")
    void getMiddleRegionTest(){
        // given
        String largeXml = "<xml>large</xml>";
        String middleXml = "<xml>middle</xml>";

        CommonResponse.ScnItem largeItem = new CommonResponse.ScnItem("11", "서울", "Y");
        CommonResponse.SrchList largeList = new CommonResponse.SrchList(List.of(largeItem));
        CommonResponse largeResponse = new CommonResponse("대분류", "1", largeList);

        CommonResponse.ScnItem middleItem = new CommonResponse.ScnItem("11110", "종로구", "Y");
        CommonResponse.SrchList middleList = new CommonResponse.SrchList(List.of(middleItem));
        CommonResponse middleResponse = new CommonResponse("중분류", "1", middleList);

        when(commonApiCaller.callCommonApi("00", null, null))
                .thenReturn(largeXml);
        when(commonResponseMapper.toRegionResponse(largeXml)).thenReturn(largeResponse);

        when(commonApiCaller.callCommonApi("01", "11", null))
                .thenReturn(middleXml);
        when(commonResponseMapper.toRegionResponse(middleXml)).thenReturn(middleResponse);

        // when
        List<CommonResponse.ScnItem> result = regionApiService.getMiddleRegion();

        // then
        assertEquals(1, result.size());
        assertEquals("서울 종로구", result.get(0).rsltName());
        assertEquals("11110", result.get(0).rsltCode());
    }
}
