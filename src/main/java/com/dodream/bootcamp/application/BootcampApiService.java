package com.dodream.bootcamp.application;

import com.dodream.bootcamp.dto.response.BootcampListApiResponse;
import com.dodream.bootcamp.infrastructure.BootcampApiCaller;
import com.dodream.bootcamp.infrastructure.BootcampDatePolicy;
import com.dodream.bootcamp.infrastructure.mapper.BootcampMapper;
import com.dodream.ncs.domain.Ncs;
import com.dodream.ncs.repository.NcsRepository;
import com.dodream.region.domain.Region;
import com.dodream.region.exception.RegionErrorCode;
import com.dodream.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BootcampApiService {

    private final BootcampApiCaller bootcampApiCaller;
    private final BootcampMapper bootcampMapper;
    private final RegionRepository regionRepository;
    private final NcsRepository ncsRepository;

    public BootcampListApiResponse getBootcampList(
            String pageNum, String regionName, String ncsName
    ) {
        String startDate = BootcampDatePolicy.calculateStartDate();
        String endDate = BootcampDatePolicy.calculateEndDate();

        String regionCode = null;
        String ncsCode = null;

        if(regionName != null && !regionName.isEmpty()) {
            Region region = regionRepository.findByRegionName(regionName)
                    .orElseThrow(RegionErrorCode.NOT_FOUND_REGION::toException);
            regionCode = region.getRegionCode();
        }

        if(ncsName != null && !ncsName.isEmpty()) {
            Ncs ncs = ncsRepository.findByNcsName(ncsName)
                    .orElseThrow(RegionErrorCode.NOT_FOUND_REGION::toException);

            ncsCode = ncs.getNcsCode();
        }

        String result = bootcampApiCaller.bootcampListApiCaller(
                pageNum, regionCode, ncsCode, startDate, endDate
        );

        return bootcampMapper.jsonStringToBootcampListApiResponse(result);
    }
}
