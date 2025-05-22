//package com.dodream.recruit;
//
//import com.dodream.recruit.application.RecruitService;
//import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
//import com.dodream.recruit.dto.response.RecruitResponseListDto;
//import com.dodream.recruit.infrastructure.RecruitApiCaller;
//import com.dodream.recruit.infrastructure.mapper.RecruitMapper;
//import com.dodream.recruit.util.RecruitCodeResolver;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.Mockito.*;
//import static org.mockito.BDDMockito.*;
//import static org.assertj.core.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class RecruitServiceTest {
//
//    @Mock
//    RecruitApiCaller recruitApiCaller;
//
//    @Mock
//    RecruitMapper recruitMapper;
//
//    @Mock
//    RecruitCodeResolver recruitCodeResolver;
//
//    @InjectMocks
//    RecruitService recruitService;
//
//    private static final int PAGE_NUMBER = 1;
//    private static final String TEST_REGION_NAME = "경기도 안양시 만안구";
//    private static final String KEYWORD = "요양 보호사";
//    private static final String RECRUIT_ID = "50671849";
//    private static final String TEST_RESULT = "{\"result\" : \"test\"}";
//
//    @Test
//    @DisplayName("[RecruitServiceTest] - 채용 결과 리스트 반환 테스트")
//    void recruitServiceTest_List(){
//        // given
//        RecruitResponseListApiDto mappedResult = mock(RecruitResponseListApiDto.class);
//        RecruitResponseListDto finalResult = mock(RecruitResponseListDto.class);
//
//        given(recruitCodeResolver.resolveRecruitLocationName(TEST_REGION_NAME))
//                .willReturn(TEST_REGION_NAME);
//        given(recruitApiCaller.recruitListApiListCaller(
//                KEYWORD, TEST_REGION_NAME, PAGE_NUMBER
//        )).willReturn(TEST_RESULT);
//        given(recruitMapper.recruitListMapper(TEST_RESULT)).willReturn(mappedResult);
//        given(recruitMapper.toSimpleListDto(mappedResult)).willReturn(finalResult);
//
//        // when
//        RecruitResponseListDto result = recruitService.getRecruitList(KEYWORD, TEST_REGION_NAME, PAGE_NUMBER);
//
//        // then
//        assertThat(result).isEqualTo(finalResult);
//        verify(recruitApiCaller, times(1)).recruitListApiListCaller(KEYWORD, TEST_REGION_NAME, PAGE_NUMBER);
//        verify(recruitMapper, times(1)).recruitListMapper(TEST_RESULT);
//        verify(recruitMapper, times(1)).toSimpleListDto(mappedResult);
//    }
//
//    @Test
//    @DisplayName("[RecruitServiceTest] - 상세정보 반환 테스트")
//    void recruitServiceTest_Detail(){
//        // given
//        RecruitResponseListApiDto mappedResult = mock(RecruitResponseListApiDto.class);
//        RecruitResponseListDto finalResult = mock(RecruitResponseListDto.class);
//
//        given(recruitApiCaller.recruitDetailAPiCaller(RECRUIT_ID)).willReturn(TEST_RESULT);
//        given(recruitMapper.recruitListMapper(TEST_RESULT)).willReturn(mappedResult);
//        given(recruitMapper.toSimpleListDto(mappedResult)).willReturn(finalResult);
//
//        // when
//        RecruitResponseListDto result = recruitService.getRecruitDetail(RECRUIT_ID);
//
//        // then
//        assertThat(result).isEqualTo(finalResult);
//        verify(recruitApiCaller, times(1)).recruitDetailAPiCaller(RECRUIT_ID);
//        verify(recruitMapper, times(1)).recruitListMapper(TEST_RESULT);
//        verify(recruitMapper, times(1)).toSimpleListDto(mappedResult);
//    }
//}
