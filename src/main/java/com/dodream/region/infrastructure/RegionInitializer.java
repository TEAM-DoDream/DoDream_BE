package com.dodream.region.infrastructure;

import com.dodream.common.dto.response.CommonResponse;
import com.dodream.region.application.RegionApiService;
import com.dodream.region.domain.Region;
import com.dodream.region.repository.RegionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class RegionInitializer {

    private final RegionRepository regionRepository;
    private final RegionApiService regionApiService;

    @PostConstruct
    public void initRegionData() {
        if(regionRepository.count() != 0) {
            // 이미 데이터 init된 경우라면 수행 x
            return;
        }

        log.info("[initRegionData] - 지역 코드 초기화 시작");

        List<CommonResponse.ScnItem> regionList = regionApiService.getMiddleRegion();

        List<Region> regionJpaEntityList = regionList.stream()
                .map(item -> Region.of(
                        item.rsltCode(),
                        item.rsltName()
                ))
                .toList();

        regionRepository.saveAll(regionJpaEntityList);
        log.info("[initRegionData] - 지역 코드 초기화 완료 {}개 저장됨.", regionJpaEntityList.size());
    }
}
