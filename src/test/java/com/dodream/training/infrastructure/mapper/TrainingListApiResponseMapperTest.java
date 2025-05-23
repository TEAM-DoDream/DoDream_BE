package com.dodream.training.infrastructure.mapper;

import com.dodream.training.dto.response.TrainingListApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[TrainingListApiResponseMapper] - 매퍼 단위 테스트")
public class TrainingListApiResponseMapperTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private TrainingListApiResponseMapper mapper;

    private String SUCCESS_RESULT;

    private final String FAIL_RESULT = "{ invalid json }";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mapper = new TrainingListApiResponseMapper(objectMapper);
        SUCCESS_RESULT = objectMapper.writeValueAsString(
                Map.of(
                        "scn_cnt", 1,
                        "pageNum", "1",
                        "pageSize", "10",
                        "srchList", List.of()
                )
        );
    }

    @Nested
    @DisplayName("[listMapper] - 리스트 매퍼 단위 테스트")
    class ListMapperTest {

        @Test
        @DisplayName("매퍼 응답 성공 테스트")
        void mapper_success(){
            // given & when
            TrainingListApiResponse result = mapper.jsonToResponseDto(SUCCESS_RESULT);

            // then
            assertThat(result.scnCnt()).isEqualTo(1);
            assertThat(result.pageNum()).isEqualTo("1");
            assertThat(result.pageSize()).isEqualTo("10");
            assertThat(result.srchList()).isEmpty();
        }

        @Test
        @DisplayName("매퍼 응답 실패 테스트")
        void mapper_fail(){
            // then
            assertThatThrownBy(() -> mapper.jsonToResponseDto(FAIL_RESULT))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("JSON -> Object 변환을 실패했습니다.");
        }
    }
}
