package com.dodream.training.infrastructure.caller;

import com.dodream.core.exception.DomainException;
import com.dodream.training.infrastructure.feign.BootcampFeignClient;
import com.dodream.training.presentation.value.SortBy;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BootcampApiCallerTest {

    @Mock
    private BootcampFeignClient bootcampFeignClient;

    @InjectMocks
    BootCampApiCaller bootcampApiCaller;

    private final String REGION_CODE = "11110";
    private final String NCS_CODE = "01010101";
    private final String PAGE_NUM = "1";
    private final SortBy SORT_BY = SortBy.DEADLINE_DESC;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(bootcampApiCaller, "apiKey", "dummy-api-key");
        ReflectionTestUtils.setField(bootcampApiCaller, "pageSize", 10);
    }

    @Nested
    @DisplayName("[getListApi] 리스트 api 테스트")
    class getListApi{

        @Test
        @DisplayName("성공 테스트")
        void getListApi_success(){
            // given
            given(bootcampFeignClient.searchBootCampList(
                    any(), any(),any(),any(),any(),any(),any(),any(),any()
            )).willReturn("response");

            // when
            String result
                    = bootcampApiCaller.getListApi(PAGE_NUM, REGION_CODE, NCS_CODE, SORT_BY);

            // then
            assertThat(result).isEqualTo("response");
        }

        @Test
        @DisplayName("실패 테스트")
        void getListApi_fail(){
            // given
            given(bootcampFeignClient.searchBootCampList(
                    any(), any(),any(),any(),any(),any(),any(),any(),any()
            )).willThrow(DomainException.class);

            // when & then
            assertThatThrownBy(() -> bootcampApiCaller.getListApi(PAGE_NUM, REGION_CODE, NCS_CODE, SORT_BY))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("외부 API 서버에서 값을 불러오는데 실패하였습니다.");
        }
    }
}
