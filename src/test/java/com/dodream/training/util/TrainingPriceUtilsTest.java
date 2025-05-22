package com.dodream.training.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class TrainingPriceUtilsTest {
    @Nested
    @DisplayName("[convertDecimalFormatForPrice] - 단위 테스트")
    class ConvertDecimalFormatForPriceTest {

        @Test
        @DisplayName("올바른 가격 입력시 반환")
        void convertDecimalFormatForPrice_success() {
            // given
            String price = "123456";

            // when
            String result = TrainingPriceUtils.convertDecimalFormatForPrice(price);

            // then
            assertThat(result).isEqualTo("123,456원");
        }

        @Test
        @DisplayName("int형 입력시 테스트")
        void convertDecimalFormatForPrice_int() {
            // given
            int price = 123456;

            // when
            String result = TrainingPriceUtils.convertDecimalFormatForPrice(price);

            // then
            assertThat(result).isEqualTo("123,456원");
        }

        @Test
        @DisplayName("잘못된 가격 입력시 동작")
        void convertDecimalFormatForPrice_fail() {
            // given
            String price = "나 가격 아니다";

            // when
            String result = TrainingPriceUtils.convertDecimalFormatForPrice(price);

            // then
            assertThat(result).isEqualTo("잘못된 가격 형식");
        }
    }
}
