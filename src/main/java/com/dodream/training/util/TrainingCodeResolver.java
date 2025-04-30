package com.dodream.training.util;

import com.dodream.ncs.domain.Ncs;
import com.dodream.ncs.exception.NcsErrorCode;
import com.dodream.ncs.repository.NcsRepository;
import com.dodream.region.domain.Region;
import com.dodream.region.exception.RegionErrorCode;
import com.dodream.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingCodeResolver {

    private final RegionRepository regionRepository;
    private final NcsRepository ncsRepository;

    public String resolveRegionCode(String regionName){
        if(regionName == null || regionName.isEmpty()) return null;

        Region region = regionRepository.findByRegionName(regionName)
                .orElseThrow(RegionErrorCode.NOT_FOUND_REGION::toException);
        return region.getRegionCode();
    }

    public String resolveNcsCode(String ncsName) {
        if (ncsName == null || ncsName.isEmpty()) return null;

        Ncs ncs = ncsRepository.findByNcsName(ncsName)
                .orElseThrow(NcsErrorCode.NOT_FOUND_NCS::toException);
        return ncs.getNcsCode();
    }
}
