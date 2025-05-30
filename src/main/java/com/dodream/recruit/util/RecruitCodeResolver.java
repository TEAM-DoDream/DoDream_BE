package com.dodream.recruit.util;

import com.dodream.recruit.exception.RecruitErrorCode;
import com.dodream.region.domain.Region;
import com.dodream.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitCodeResolver {

    private final RegionRepository regionRepository;

    public String resolveRecruitLocationName(String locName){
        return regionRepository.findByRegionName(locName)
                .map(Region::getSaraminRegionCode)
                .orElse(null);
    }
}
