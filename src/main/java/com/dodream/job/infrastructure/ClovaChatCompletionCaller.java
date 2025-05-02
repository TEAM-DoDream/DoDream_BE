package com.dodream.job.infrastructure;

import com.dodream.job.dto.request.clova.ClovaMessage;
import com.dodream.job.dto.request.clova.ClovaStudioRequest;
import com.dodream.job.exception.JobErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClovaChatCompletionCaller {

    private final ClovaFeignClient clovaFeignClient;

    @Value("${ncp.clova.max-token}")
    private int maxToken;

    public String clovaChatCompletionApiCaller(
            String systemMessage, String userMessage
    ){
        ClovaStudioRequest clovaStudioRequest = new ClovaStudioRequest(
                List.of(
                    ClovaMessage.createSystemMessage(systemMessage),
                    ClovaMessage.createUserMessage(userMessage)
                ),
                maxToken
        );

        try{
            return clovaFeignClient.callClovaStudio(
                    clovaStudioRequest
            );
        } catch (Exception e){
            throw JobErrorCode.CLOVA_API_CALL_FAILED.toException();
        }

    }
}
