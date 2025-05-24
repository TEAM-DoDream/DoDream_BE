package com.dodream.region.dto.response;

import com.dodream.region.domain.Region;

public record RegionResponseDto(
        String regionCode,
        String regionName
) {
    public static RegionResponseDto from(Region region) {
        return new RegionResponseDto(
                region.getRegionCode(), region.getRegionName()
        );
    }
}
