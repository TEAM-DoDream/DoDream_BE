package com.dodream.region.application;

import com.dodream.region.dto.response.RegionResponse;
import com.dodream.region.exception.RegionErrorCode;
import com.dodream.region.infrastructure.RegionApiProperties;
import com.dodream.region.infrastructure.RegionFeignClient;
import com.dodream.core.infrastructure.converter.XmlToJsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegionService {

    private final RegionFeignClient regionFeignClient;
    private final RegionApiProperties regionApiProperties;
    private final XmlToJsonConverter xmlToJsonConverter;
    private final ObjectMapper objectMapper;

    public RegionResponse.SrchList getLargeRegionCode() {
        String regionXML = regionFeignClient.getRegionCode(
                regionApiProperties.getApiKey(),
                "XML",
                "1",
                "00",
                null,
                null
        );

        String regionJSON = xmlToJsonConverter.convertXmlToJson(regionXML);

        try {
            RegionResponse response = objectMapper.readValue(regionJSON, RegionResponse.class);
            return response.srchList();
        } catch (JsonProcessingException e) {
            throw RegionErrorCode.JSON_TO_OBJECT_CONVERT_ERROR.toException();
        }
    }

    public List<RegionResponse.ScnItem> getMiddleRegion() {
        List<RegionResponse.ScnItem> largeRegions = getLargeRegionCode().scnList();
        List<RegionResponse.ScnItem> result = new ArrayList<>();

        Map<String, String> largeCodeToName = largeRegions.stream()
                .collect(Collectors.toMap(RegionResponse.ScnItem::rsltCode, RegionResponse.ScnItem::rsltName));

        for(RegionResponse.ScnItem largeRegionItem : largeRegions){
            if(largeRegionItem.useYn().equals("N")) continue;

            String largeCode = largeRegionItem.rsltCode();

            String middleXML = regionFeignClient.getRegionCode(
                    regionApiProperties.getApiKey(),
                    "XML",
                    "1",
                    "01",
                    largeCode,
                    null
            );

            String regionJSON = xmlToJsonConverter.convertXmlToJson(middleXML);

            try {
                RegionResponse response = objectMapper.readValue(regionJSON, RegionResponse.class);
                List<RegionResponse.ScnItem> scnList = response.srchList().scnList();
                for (RegionResponse.ScnItem item : scnList) {
                    if(item.useYn().equals("N")) continue;
                    String fullName = largeCodeToName.get(largeCode) + " " + item.rsltName().trim();

                    RegionResponse.ScnItem newItem = new RegionResponse.ScnItem(
                            item.rsltCode(),
                            fullName,
                            item.useYn()
                    );

                    result.add(newItem);
                }
            } catch (JsonProcessingException e) {
                throw RegionErrorCode.JSON_TO_OBJECT_CONVERT_ERROR.toException();
            }
        }

        return result;
    }
}
