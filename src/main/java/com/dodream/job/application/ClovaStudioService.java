package com.dodream.job.application;

import com.dodream.job.dto.response.ChatResponse;
import com.dodream.job.infrastructure.ClovaChatCompletionCaller;
import com.dodream.job.infrastructure.ClovaChatResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClovaStudioService {

    private final ClovaChatCompletionCaller clovaChatCompletionCaller;
    private final ClovaChatResponseMapper clovaChatResponseMapper;

    public ChatResponse chatCompletionService(
            String systemMessage,
            String userMessage
    ){
        String result = clovaChatCompletionCaller.clovaChatCompletionApiCaller(
                systemMessage,
                userMessage
        );

        return clovaChatResponseMapper.jsonToChatResponse(result);
    }
}
