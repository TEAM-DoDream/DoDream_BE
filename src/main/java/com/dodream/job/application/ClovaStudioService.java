package com.dodream.job.application;

import com.dodream.job.infrastructure.ClovaChatCompletionCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClovaStudioService {

    private final ClovaChatCompletionCaller clovaChatCompletionCaller;

    public String chatCompletionService(
            String systemMessage,
            String userMessage
    ){
        return clovaChatCompletionCaller.clovaChatCompletionApiCaller(
                systemMessage,
                userMessage
        );
    }
}
