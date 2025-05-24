package com.dodream.common.infrastructure;

import com.dodream.common.exception.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonApiCaller {

    private final CommonFeignClient commonFeignClient;
    private final CommonApiProperties commonApiProperties;

    public String callCommonApi(String searchType1, String searchOption1, String searchOption2) {
        try {
            return commonFeignClient.getCommonCode(
                    commonApiProperties.getApiKey(),
                    "XML",
                    "1",
                    searchType1,
                    searchOption1,
                    searchOption2
            );
        }catch(Exception e) {
            throw CommonErrorCode.EXTERNAL_API_CONNECTION_ERROR.toException();
        }
    }
}
