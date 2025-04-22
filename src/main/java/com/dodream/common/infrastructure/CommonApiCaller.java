package com.dodream.common.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonApiCaller {

    private final CommonFeignClient commonFeignClient;
    private final CommonApiProperties commonApiProperties;

    public String callCommonApi(String searchType1, String searchOption1, String searchOption2) {
        return commonFeignClient.getCommonCode(
                commonApiProperties.getApiKey(),
                "XML",
                "1",
                searchType1,
                searchOption1,
                searchOption2
        );
    }
}
