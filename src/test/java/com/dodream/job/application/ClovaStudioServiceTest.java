package com.dodream.job.application;

import com.dodream.job.dto.response.ChatResponse;
import com.dodream.job.infrastructure.caller.ClovaChatCompletionCaller;
import com.dodream.job.infrastructure.mapper.ClovaChatResponseMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ClovaStudioServiceTest {

    @Mock
    ClovaChatCompletionCaller clovaChatCompletionCaller;

    @Mock
    ClovaChatResponseMapper clovaChatResponseMapper;

    @InjectMocks
    ClovaStudioService clovaStudioService;

    @Test
    @DisplayName("[chatCompletionService] 클로바 api 호출 테스트")
    void chatCompletionServiceTest_success() {
        // given
        String system = "system";
        String user = "user";
        String result = "response";

        ChatResponse.Message message = new ChatResponse.Message("assistant", "{...}");
        ChatResponse.Usage usage = new ChatResponse.Usage(10, 10, 20);
        ChatResponse.Result chatResult = new ChatResponse.Result(message, "stop", usage);
        ChatResponse chatResponse = new ChatResponse(chatResult);

        given(clovaChatCompletionCaller.clovaChatCompletionApiCaller(system, user)).willReturn(result);
        given(clovaChatResponseMapper.jsonToChatResponse(result)).willReturn(chatResponse);

        // when
        ChatResponse response = clovaStudioService.chatCompletionService(system, user);

        // then
        assertThat(response).isNotNull();
        assertThat(response.result().usage()).isEqualTo(usage);
    }
}
