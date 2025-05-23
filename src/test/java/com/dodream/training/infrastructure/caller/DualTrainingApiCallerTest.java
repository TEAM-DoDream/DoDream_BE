package com.dodream.training.infrastructure.caller;

import com.dodream.core.exception.DomainException;
import com.dodream.training.infrastructure.feign.DualTrainingFeignClient;
import com.dodream.training.presentation.value.SortBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DualTrainingApiCallerTest {

    @Mock
    DualTrainingFeignClient dualTrainingFeignClient;

    @InjectMocks
    DualTrainingApiCaller dualTrainingApiCaller;

    private final String REGION_CODE = "41110";
    private final String NCS_CODE = "02010201";
    private final String PAGE_NUM = "2";
    private final SortBy SORT_BY = SortBy.DEADLINE_DESC;

    private final String TRPR_ID = "A987654321";
    private final String TRPR_DEG = "3";
    private final String TORG_ID = "321987654";

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(dualTrainingApiCaller, "apiKey", "dummy-api-key");
        ReflectionTestUtils.setField(dualTrainingApiCaller, "pageSize", 10);
    }

    @Nested
    @DisplayName("[getListApi] 리스트 Api 테스트")
    class GetListApiTest{

        @Test
        @DisplayName("리스트 반환 성공")
        void getListApi_success(){
            // given
            given(dualTrainingFeignClient.getDualTrainingList(
                    any(),any(),any(),any(),any(),any(),any(),any(),any()
            )).willReturn("response");

            // when
            String result
                    = dualTrainingApiCaller.getListApi(PAGE_NUM, REGION_CODE, NCS_CODE, SORT_BY);

            // then
            assertThat(result).isEqualTo("response");
        }

        @Test
        @DisplayName("리스트 반환 실패")
        void getListApi_fail(){
            // given
            given(dualTrainingFeignClient.getDualTrainingList(
                    any(),any(),any(),any(),any(),any(),any(),any(),any()
            )).willThrow(DomainException.class);

            // when & then
            assertThatThrownBy(() -> dualTrainingApiCaller.getListApi(PAGE_NUM, REGION_CODE, NCS_CODE, SORT_BY))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("외부 API 서버에서 값을 불러오는데 실패하였습니다.");
        }
    }

    @Nested
    @DisplayName("[getDetailApi] 상세 정보 반환 api 테스트")
    class GetDetailApiTest{

        @Test
        @DisplayName("상세 정보 반환 성공")
        void getDetailApi_success(){
            // given
            given(dualTrainingFeignClient.getDualTrainingDetail(
                    any(),any(),any(),any(),any(),any()
            )).willReturn("response");

            // when
            String result
                    = dualTrainingApiCaller.getDetailApi(TRPR_ID, TRPR_DEG, TORG_ID);

            // then
            assertThat(result).isEqualTo("response");
        }

        @Test
        @DisplayName("상세 정보 반환 실패")
        void getDetailApi_fail(){
            // given
            given(dualTrainingFeignClient.getDualTrainingDetail(
                    any(),any(),any(),any(),any(),any()
            )).willThrow(DomainException.class);

            // when & then
            assertThatThrownBy(() -> dualTrainingApiCaller.getDetailApi(TRPR_ID, TRPR_DEG, TORG_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("외부 API 서버에서 값을 불러오는데 실패하였습니다.");
        }
    }
}
