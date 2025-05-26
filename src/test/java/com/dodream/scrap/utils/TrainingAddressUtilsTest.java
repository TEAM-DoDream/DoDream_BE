package com.dodream.scrap.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[TrainingAddressUtils] - 단위 테스트")
public class TrainingAddressUtilsTest {

    @Test
    @DisplayName("주소 추출 테스트")
    void testTypicalAddress(){
        String address = "경기도 안양시 만안구 안양천서로 289";
        String expected = "경기도 안양시 만안구";

        Assertions.assertEquals(expected, TrainingAddressUtils.extractMainRegion(address));
    }

    @Test
    @DisplayName("동이 있는 주소 추출")
    void testAddressWithCity(){
        String address = "경기도 안양시 만안구 안양1동";
        String expected = "경기도 안양시 만안구";

        Assertions.assertEquals(expected, TrainingAddressUtils.extractMainRegion(address));
    }

    @Test
    @DisplayName("구만 있는 도시 추출")
    void testAddressWithCounty(){
        String address = "서울시 강남구";
        String expected = "서울시 강남구";

        Assertions.assertEquals(expected, TrainingAddressUtils.extractMainRegion(address));
    }

    @Test
    @DisplayName("빈 문자열 테스트")
    void emptyStringTest(){
        Assertions.assertEquals("", TrainingAddressUtils.extractMainRegion(""));
    }

    @Test
    @DisplayName("null 문자열 테스트")
    void nullTest(){
        Assertions.assertEquals("", TrainingAddressUtils.extractMainRegion(null));
    }
}
