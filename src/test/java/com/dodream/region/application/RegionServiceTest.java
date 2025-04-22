package com.dodream.region.application;

import com.dodream.core.exception.DomainException;
import com.dodream.region.domain.Region;
import com.dodream.region.repository.RegionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("[RegionService] - 테스트")
public class RegionServiceTest {
    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    private static final String TEST_CODE = "31110";
    private static final String TEST_NAME = "경기 안양시 만안구";

    @Nested
    @DisplayName("전체 조회 테스트")
    class FindAllRegionTest{

        @Test
        @DisplayName("[findAllRegions] - 전체 조회")
        void findAllRegionsSuccess(){
            // given
            Region item1 = mock(Region.class);
            Region item2 = mock(Region.class);

            List<Region> regionList = List.of(item1, item2);

            when(item1.getRegionCode()).thenReturn(TEST_CODE);
            when(item2.getRegionCode()).thenReturn(TEST_CODE);

            when(item1.getRegionName()).thenReturn(TEST_NAME);
            when(item2.getRegionName()).thenReturn(TEST_NAME);

            when(regionRepository.findAll()).thenReturn(regionList);

            // when
            List<RegionResponseDto> result = regionService.findAllRegions();

            // then
            assertEquals(2, result.size());
        }
    }

    @Nested
    @DisplayName("[findByName] - 이름으로 조회")
    class FindByNameTest{

        @Test
        @DisplayName("이름으로 조회 성공")
        void findByNameSuccess(){
            // given
            Region region = mock(Region.class);

            when(region.getRegionCode()).thenReturn(TEST_CODE);
            when(region.getRegionName()).thenReturn(TEST_NAME);

            when(regionRepository.findByRegionName(TEST_NAME)).thenReturn(Optional.of(region));

            // when
            RegionResponseDto result = regionService.findByName(TEST_NAME);

            // then
            assertEquals(result.regionCode(), TEST_CODE);
            assertEquals(result.regionName(), TEST_NAME);
        }

        @Test
        @DisplayName("이름으로 조회 실패")
        void findByNameFail(){
            // given
            when(regionRepository.findByRegionName(TEST_NAME)).thenReturn(Optional.empty());

            // when
            Assertions.assertThatThrownBy(() -> regionService.findByName(TEST_NAME))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("지역 정보를 찾을 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("[findByRegionCode] - 지역 코드로 조회")
    class FindByRegionCodeTest{

        @Test
        @DisplayName("지역 코드로 조회 성공")
        void findByRegionCodeSuccess(){
            // given
            Region region = mock(Region.class);

            when(region.getRegionCode()).thenReturn(TEST_CODE);
            when(region.getRegionName()).thenReturn(TEST_NAME);

            when(regionRepository.findByRegionCode(TEST_CODE)).thenReturn(Optional.of(region));

            // when
            RegionResponseDto result = regionService.findByRegionCode(TEST_CODE);

            // then
            assertEquals(result.regionCode(), TEST_CODE);
            assertEquals(result.regionName(), TEST_NAME);
        }

        @Test
        @DisplayName("지역 코드로 조회 실패")
        void findByRegionCodeFail(){
            // given
            when(regionRepository.findByRegionCode(TEST_CODE)).thenReturn(Optional.empty());

            // when & then
            Assertions.assertThatThrownBy(() -> regionService.findByRegionCode(TEST_CODE))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("지역 정보를 찾을 수 없습니다.");
        }
    }
}
