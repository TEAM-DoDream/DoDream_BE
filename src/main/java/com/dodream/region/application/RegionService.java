package com.dodream.region.application;

import com.dodream.region.infrastructure.RegionApiProperties;
import com.dodream.region.infrastructure.RegionFeignClient;
import com.dodream.core.infrastructure.converter.XmlToJsonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionFeignClient regionFeignClient;
    private final RegionApiProperties regionApiProperties;
    private final XmlToJsonConverter xmlToJsonConverter;

    public String getRegionCode(
            String searchType1,
            String searchOption1,
            String searchOption2
    ) {
        String regionXML = regionFeignClient.getRegionCode(
                regionApiProperties.getApiKey(),
                "XML",
                "1",
                searchType1,
                searchOption1,
                searchOption2
        );

        return xmlToJsonConverter.convertXmlToJson(regionXML);
    }
}
