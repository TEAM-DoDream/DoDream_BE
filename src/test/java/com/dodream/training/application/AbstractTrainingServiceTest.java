package com.dodream.training.application;

import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import com.dodream.training.presentation.value.SortBy;
import com.dodream.training.util.TrainingCodeResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractTrainingServiceTest<T extends AbstractTrainingService> {

    @Mock
    protected TrainingMapper<TrainingListApiResponse> listMapper;

    @Mock
    protected TrainingMapper<TrainingDetailApiResponse> detailMapper;

    @Mock
    protected TrainingCodeResolver trainingCodeResolver;

    protected T service;

    protected abstract T getService();

    protected abstract TrainingApiCaller getApiCaller();

    protected final String REGION_NAME = "서울 종로구";
    protected final String JOB_NAME = "요양보호사";
    protected final String REGION_CODE = "11110";
    protected final String NCS_CODE = "01010101";

    protected String TRPR_ID = "12345678";
    protected String TRPR_DEG = "3";
    protected String TORG_ID = "12345678";

    protected String JSON_RESULT = "{...}";

    protected final TrainingListApiResponse DUMMY_RESPONSE =
            new TrainingListApiResponse(1, "1", "10", List.of());
    protected final TrainingDetailApiResponse DUMMY_DETAIL =
            new TrainingDetailApiResponse(
                    new TrainingDetailApiResponse.InstBaseInfo(
                            "https://~",
                            "경기도 안양시 만안구",
                            3,
                            "요양보호사 자격증 과정",
                            "요양보호사 교육기관",
                            123456
                    ), new TrainingDetailApiResponse.InstDetailInfo("350000")
            );
    protected final SortBy DEFAULT_SORT = SortBy.DEADLINE_DESC;

    @BeforeEach
    void setUp(){
        this.service = getService();
    }

    @Nested
    @DisplayName("[AbstractTrainingService] - 리스트 반환 테스트")
    class BootcampListTest{

        @Test
        @DisplayName("리스트 정상 반환 테스트")
        void bootcampList_success(){
            // given
            when(trainingCodeResolver.resolveRegionCode(REGION_NAME)).thenReturn(REGION_CODE);
            when(trainingCodeResolver.resolveNcsCode(JOB_NAME)).thenReturn(NCS_CODE);
            when(getApiCaller().getListApi("1", REGION_CODE, NCS_CODE, DEFAULT_SORT))
                    .thenReturn(JSON_RESULT);
            when(listMapper.jsonToResponseDto(JSON_RESULT)).thenReturn(DUMMY_RESPONSE);

            // when
            TrainingListApiResponse result
                    = service.getList("1", REGION_NAME, JOB_NAME, DEFAULT_SORT);

            // then
            assertThat(result.scnCnt()).isEqualTo(1);
            assertThat(result.srchList()).isEqualTo(List.of());
        }
    }

    @Nested
    @DisplayName("[AbstractTrainingService] - 상세 정보 반환 테스트")
    class BootcampDetailTest{

        @Test
        @DisplayName("상세 정보 정상 반환 테스트")
        void bootcampDetail_success(){
            // given
            when(getApiCaller().getDetailApi(TRPR_ID, TRPR_DEG, TORG_ID)).thenReturn(JSON_RESULT);
            when(detailMapper.jsonToResponseDto(JSON_RESULT)).thenReturn(DUMMY_DETAIL);

            // when
            TrainingDetailApiResponse result = service.getDetail(TRPR_ID, TRPR_DEG, TORG_ID);

            // then
            assertThat(result.instBaseInfo()).isNotNull();
            assertThat(result.instBaseInfo().addr()).isEqualTo("경기도 안양시 만안구");
        }
    }
}
