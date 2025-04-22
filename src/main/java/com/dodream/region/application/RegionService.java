package com.dodream.region.application;

import com.dodream.region.domain.Region;
import com.dodream.region.exception.RegionErrorCode;
import com.dodream.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public List<RegionResponseDto> findAllRegions(){
        return regionRepository.findAll()
                .stream()
                .map(RegionResponseDto::from)
                .toList();
    }

    public RegionResponseDto findByName(String name){
        Region region = regionRepository.findByRegionName(name)
                .orElseThrow(RegionErrorCode.NOT_FOUND_REGION::toException);

        return RegionResponseDto.from(region);
    }

    public RegionResponseDto findByRegionCode(String code){
        Region region = regionRepository.findByRegionCode(code)
                .orElseThrow(RegionErrorCode.NOT_FOUND_REGION::toException);

        return RegionResponseDto.from(region);
    }
}
