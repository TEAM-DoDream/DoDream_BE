package com.dodream.ncs;

import com.dodream.common.dto.response.CommonResponse;
import com.dodream.common.dto.response.CommonResponse.ScnItem;
import com.dodream.common.dto.response.CommonResponse.SrchList;
import com.dodream.common.infrastructure.CommonApiCaller;
import com.dodream.common.infrastructure.mapper.CommonResponseMapper;
import com.dodream.ncs.application.NcsApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NcsApiServiceTest {

    @Mock
    private CommonApiCaller commonApiCaller;

    @Mock
    private CommonResponseMapper commonResponseMapper;

    @InjectMocks
    private NcsApiService ncsApiService;

    @Test
    @DisplayName("대분류 NCS 정보 조회 성공 테스트")
    void getLargeNcsInfoTest() {
        // given
        String xml = "<mocked-xml/>";
        List<ScnItem> itemList = List.of(
                new ScnItem("01", "직무1", "Y"),
                new ScnItem("02", "직무2", "Y")
        );
        SrchList srchList = new SrchList(itemList);
        CommonResponse mockResponse = new CommonResponse("직무", "2", srchList);

        when(commonApiCaller.callCommonApi("05", null, null)).thenReturn(xml);
        when(commonResponseMapper.toCommonResponse(xml)).thenReturn(mockResponse);

        // when
        SrchList result = ncsApiService.getLargeNcsInfo();

        // then
        assertEquals(2, result.scnList().size());
        verify(commonApiCaller).callCommonApi("05", null, null);
    }

    @Test
    @DisplayName("중분류 NCS 정보 조회 성공 테스트")
    void getMiddleNcsInfoTest() {
        // given
        String xml = "<mocked-xml/>";
        List<ScnItem> itemList = List.of(
                new ScnItem("0101", "직무1", "Y"),
                new ScnItem("0201", "직무2", "Y")
        );
        SrchList srchList = new SrchList(itemList);
        CommonResponse mockResponse = new CommonResponse("직무", "2", srchList);

        when(commonApiCaller.callCommonApi("06", null, null)).thenReturn(xml);
        when(commonResponseMapper.toCommonResponse(xml)).thenReturn(mockResponse);

        // when
        SrchList result = ncsApiService.getMiddleNcsInfo();

        // then
        assertEquals(2, result.scnList().size());
        verify(commonApiCaller).callCommonApi("06", null, null);
    }

    @Test
    @DisplayName("소분류 NCS 정보 조회 성공 테스트")
    void getSmallNcsInfoTest() {
        // given
        String xml = "<mocked-xml/>";
        List<ScnItem> itemList = List.of(
                new ScnItem("010101", "직무1", "Y"),
                new ScnItem("020101", "직무2", "Y")
        );
        SrchList srchList = new SrchList(itemList);
        CommonResponse mockResponse = new CommonResponse("직무", "2", srchList);

        when(commonApiCaller.callCommonApi("07", null, null)).thenReturn(xml);
        when(commonResponseMapper.toCommonResponse(xml)).thenReturn(mockResponse);

        // when
        SrchList result = ncsApiService.getSmallNcsInfo();

        // then
        assertEquals(2, result.scnList().size());
        verify(commonApiCaller).callCommonApi("07", null, null);
    }

    @Test
    @DisplayName("세분류 NCS 정보 조회 성공 테스트")
    void getSmallestNcsInfoTest() {
        // given
        String xml = "<mocked-xml/>";
        List<ScnItem> itemList = List.of(
                new ScnItem("01010101", "직무1", "Y"),
                new ScnItem("02010101", "직무2", "Y")
        );
        SrchList srchList = new SrchList(itemList);
        CommonResponse mockResponse = new CommonResponse("직무", "2", srchList);

        when(commonApiCaller.callCommonApi("08", null, null)).thenReturn(xml);
        when(commonResponseMapper.toCommonResponse(xml)).thenReturn(mockResponse);

        // when
        SrchList result = ncsApiService.getSmallestNcsInfo();

        // then
        assertEquals(2, result.scnList().size());
        verify(commonApiCaller).callCommonApi("08", null, null);
    }
}
