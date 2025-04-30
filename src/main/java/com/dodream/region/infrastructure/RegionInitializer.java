package com.dodream.region.infrastructure;

import com.dodream.common.dto.response.CommonResponse;
import com.dodream.region.application.RegionApiService;
import com.dodream.region.domain.Region;
import com.dodream.region.repository.RegionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        updateSaraminRegionCode();
    }

    private void updateSaraminRegionCode() {
        log.info("[updateSaraminRegionCode] - 사람인 지역코드 업데이트 시작");

        List<SaraminRegionMapping> mappings = loadSaraminRegionMappingsFromCsv();

        for (SaraminRegionMapping mapping : mappings) {
            regionRepository.findByRegionName(mapping.regionName())
                    .ifPresent(region -> {
                        region.updateSaraminRegionCode(mapping.saraminRegionCode());
                        regionRepository.save(region);
                    });
        }
    }

    private List<SaraminRegionMapping> loadSaraminRegionMappingsFromCsv() {
        List<SaraminRegionMapping> mappings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader()
                        .getResourceAsStream("saramin_region.csv")), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] columns = line.split(",");
                if (columns.length < 2) continue; // 이상한 라인 skip

                String saraminRegionCode = columns[0].trim();
                String regionName = columns[1];
                mappings.add(new SaraminRegionMapping(saraminRegionCode, regionName));
            }
        } catch (Exception e) {
            log.error("[updateSaraminRegionCode] - CSV 읽기 실패", e);
        }
        return mappings;
    }

    // 내부 record 클래스로 깔끔하게 관리
    private record SaraminRegionMapping(String saraminRegionCode, String regionName) {}
}
