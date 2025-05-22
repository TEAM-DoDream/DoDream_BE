package com.dodream.training.util;

import com.dodream.core.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TrainingDateUtilsTest {

    @Nested
    @DisplayName("calculateDuration - 기간 계산 테스트")
    class CalculateDurationTest {

        @Test
        @DisplayName("30일 미만: 일 단위 반환")
        void calculateDuration_lessThanMonth() {
            String result = TrainingDateUtils.calculateDuration("2024-01-01", "2024-01-10");
            assertThat(result).isEqualTo("9일");
        }

        @Test
        @DisplayName("정확히 2개월")
        void calculateDuration_exactTwoMonths() {
            String result = TrainingDateUtils.calculateDuration("2024-01-01", "2024-03-01");
            assertThat(result).isEqualTo("약 2개월");
        }

        @Test
        @DisplayName("1개월 15일: 반올림으로 2개월")
        void calculateDuration_roundUp() {
            String result = TrainingDateUtils.calculateDuration("2024-01-01", "2024-02-16");
            assertThat(result).isEqualTo("약 2개월");
        }

        @Test
        @DisplayName("1개월 14일: 반올림 안됨, 1개월 유지")
        void calculateDuration_noRoundUp() {
            String result = TrainingDateUtils.calculateDuration("2024-01-01", "2024-02-14");
            assertThat(result).isEqualTo("약 1개월");
        }
    }

    @Nested
    @DisplayName("convertDateFormat - 날짜 포맷 변환 테스트")
    class ConvertDateFormatTest {

        @Test
        @DisplayName("yyyy-MM-dd → yyyy.MM.dd 포맷 변환")
        void convertDateFormat_success() {
            String result = TrainingDateUtils.convertDateFormat("2024-01-15");
            assertThat(result).isEqualTo("2024.01.15");
        }

        @Test
        @DisplayName("잘못된 포맷 입력시 오류")
        void convertDateFormat_error() {
            // when & then
            assertThatThrownBy(() -> TrainingDateUtils.convertDateFormat("2024.05.26"))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("날짜 형식 변환에 실패했습니다.");
        }
    }
}
