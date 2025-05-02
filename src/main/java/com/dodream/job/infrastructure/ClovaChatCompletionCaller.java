package com.dodream.job.infrastructure;

import com.dodream.job.dto.request.clova.ClovaMessage;
import com.dodream.job.dto.request.clova.ClovaStudioRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClovaChatCompletionCaller {

    private final ClovaFeignClient clovaFeignClient;

    private static final int MAX_TOKEN = 512;

    private static final String CLOVA_MODEL = "HCX-DASH-002";

    public String clovaChatCompletionApiCaller(
            String systemMessage, String userMessage
    ){
        ClovaStudioRequest clovaStudioRequest = new ClovaStudioRequest(
                List.of(
                    ClovaMessage.createSystemMessage(systemMessage),
                    ClovaMessage.createUserMessage(userMessage)
                ),
                MAX_TOKEN
        );

        return clovaFeignClient.callClovaStudio(
                CLOVA_MODEL,
                clovaStudioRequest
        );
    }
}
