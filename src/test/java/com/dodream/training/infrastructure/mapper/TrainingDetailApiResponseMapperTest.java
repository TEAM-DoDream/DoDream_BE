package com.dodream.training.infrastructure.mapper;

import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[TrainingDetailApiResponseMapperTest] - 상세 정보 매퍼 테스트")
public class TrainingDetailApiResponseMapperTest {

    ObjectMapper objectMapper = new ObjectMapper();

    TrainingDetailResponseDtoMapper mapper;

    private final String SUCCESS_RESULT;

    private final String FAIL_RESULT = "{ invalid json }";

    public TrainingDetailApiResponseMapperTest() throws JsonProcessingException {
        SUCCESS_RESULT = objectMapper.writeValueAsString(
                Map.of(
                        "inst_base_info", Map.of(
                                "addr1", "경기도 안양시 만안구",
                                "trprDegr", 3,
                                "trprNm", "요양보호사 자격증 취득 과정 (사회복지사)",
                                "inoNm", "느티나무요양보호사교육원",
                                "instPerTrco", 250000
                        )
                )
        );
    }

    @BeforeEach
    void setUp(){
        mapper = new TrainingDetailResponseDtoMapper(objectMapper);
    }

    @Nested
    @DisplayName("[detailMapper] - 상세 정보 변환 매퍼 테스트")
    class DetailMapperTest {

        @Test
        @DisplayName("상세 정보 매퍼 성공 반환")
        void detailMapper_success(){
            // given & when
            TrainingDetailApiResponse result = mapper.jsonToResponseDto(SUCCESS_RESULT);

            // then
            assertThat(result.instBaseInfo()).isNotNull();
            assertThat(result.instBaseInfo().addr()).isEqualTo("경기도 안양시 만안구");
            assertThat(result.instBaseInfo().trprDegr()).isEqualTo(3);
            assertThat(result.instBaseInfo().instPerTrco()).isEqualTo(250000);
        }

        @Test
        @DisplayName("상세 정보 매퍼 오류 반환")
        void detailMapper_fail(){
            // given & when & then
            assertThatThrownBy(() -> mapper.jsonToResponseDto(FAIL_RESULT))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("JSON -> Object 변환을 실패했습니다.");
        }
    }
}
