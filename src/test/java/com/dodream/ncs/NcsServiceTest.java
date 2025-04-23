package com.dodream.ncs;

import com.dodream.core.exception.DomainException;
import com.dodream.ncs.application.NcsService;
import com.dodream.ncs.domain.Ncs;
import com.dodream.ncs.dto.response.NcsResponseDto;
import com.dodream.ncs.repository.NcsRepository;
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
public class NcsServiceTest {

    @Mock
    private NcsRepository ncsRepository;

    @InjectMocks
    private NcsService ncsService;

    private static final String TEST_NCS_CODE = "12";
    private static final String TEST_NCS_NAME = "이용·숙박·여행·오락·스포츠";

    @Nested
    @DisplayName("[NcsServiceTest] - 전체 조회 테스트")
    class getAllNcsTest{

        @Test
        @DisplayName("전체 조회 성공 테스트")
        void getAllNcsTestSuccess(){
            // given
            Ncs ncs1 = mock(Ncs.class);
            Ncs ncs2 = mock(Ncs.class);

            when(ncs1.getNcsCode()).thenReturn(TEST_NCS_CODE);
            when(ncs2.getNcsCode()).thenReturn(TEST_NCS_CODE);

            when(ncs1.getNcsName()).thenReturn(TEST_NCS_NAME);
            when(ncs2.getNcsName()).thenReturn(TEST_NCS_NAME);

            List<Ncs> ncsList = List.of(ncs1, ncs2);

            when(ncsRepository.findAll()).thenReturn(ncsList);

            // when
            List<NcsResponseDto> result = ncsService.getAllNcs();

            // then
            assertEquals(2, result.size());
        }
    }

    @Nested
    @DisplayName("[NcsServiceTest] - 직무 이름으로 조회 테스트")
    class getNcsByNameTest{

        @Test
        @DisplayName("직무 이름으로 조회 성공 테스트")
        void getNcsByNameTestSuccess(){
            // given
            Ncs ncs1 = mock(Ncs.class);

            when(ncs1.getNcsCode()).thenReturn(TEST_NCS_CODE);
            when(ncs1.getNcsName()).thenReturn(TEST_NCS_NAME);

            when(ncsRepository.findByNcsName(TEST_NCS_NAME)).thenReturn(Optional.of(ncs1));

            // when
            NcsResponseDto result = ncsService.getNcsByNcsName(TEST_NCS_NAME);

            // then
            assertEquals(result.ncsName(), TEST_NCS_NAME);
            assertEquals(result.ncsCode(), TEST_NCS_CODE);
        }

        @Test
        @DisplayName("직무 이름으로 조회 실패 테스트")
        void getNcsByNameTestFail(){
            // given
            when(ncsRepository.findByNcsName(TEST_NCS_NAME)).thenReturn(Optional.empty());

            // when & then
            Assertions.assertThatThrownBy(() -> ncsService.getNcsByNcsName(TEST_NCS_NAME))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("NCS 데이터를 찾을 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("[NcsServiceTest] - 직무 코드로 조회 테스트")
    class getNcsByCodeTest{

        @Test
        @DisplayName("직무 코드로 조회 성공")
        void getNcsByCodeTestSuccess(){
            // given
            Ncs ncs1 = mock(Ncs.class);

            when(ncs1.getNcsCode()).thenReturn(TEST_NCS_CODE);
            when(ncs1.getNcsName()).thenReturn(TEST_NCS_NAME);

            when(ncsRepository.findByNcsCode(TEST_NCS_CODE)).thenReturn(Optional.of(ncs1));

            // when
            NcsResponseDto result = ncsService.getNcsByNcsCode(TEST_NCS_CODE);

            // then
            assertEquals(result.ncsCode(), TEST_NCS_CODE);
            assertEquals(result.ncsName(), TEST_NCS_NAME);
        }

        @Test
        @DisplayName("직무 코드로 조회 실패 테스트")
        void getNcsByCodeTestFail(){
            // given
            when(ncsRepository.findByNcsCode(TEST_NCS_CODE)).thenReturn(Optional.empty());

            // when & then
            Assertions.assertThatThrownBy(() -> ncsService.getNcsByNcsCode(TEST_NCS_CODE))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("NCS 데이터를 찾을 수 없습니다.");
        }
    }
}
