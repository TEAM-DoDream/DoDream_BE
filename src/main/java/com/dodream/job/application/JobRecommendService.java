package com.dodream.job.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.job.domain.Job;
import com.dodream.job.dto.request.recommend.ExampleJobList;
import com.dodream.job.dto.request.recommend.JobRecommendationResponse;
import com.dodream.job.dto.request.recommend.OnboardingAnswerSet;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.infrastructure.caller.ClovaChatCompletionCaller;
import com.dodream.job.infrastructure.factory.SystemPromptLoader;
import com.dodream.job.infrastructure.factory.UserPromptLoader;
import com.dodream.job.infrastructure.mapper.ClovaChatResponseMapper;
import com.dodream.job.infrastructure.mapper.JsonCleaner;
import com.dodream.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobRecommendService {

    private final JobRepository jobRepository;
    private final ClovaChatCompletionCaller clovaChatCompletionCaller;
    private final ClovaChatResponseMapper clovaChatResponseMapper;

    public JobRecommendationResponse recommendJob(CustomUserDetails customUserDetails, OnboardingAnswerSet answerSet) {
        String result = clovaChatCompletionCaller.clovaChatCompletionApiCaller(
                SystemPromptLoader.getPrompt(getExampleJobList(jobRepository.findAll())),
                UserPromptLoader.getPrompt(customUserDetails.getUsername(), answerSet)
        );

        try{
            return JsonCleaner.cleanAndParse(getContentFromResult(result), JobRecommendationResponse.class);
        } catch (Exception e){
            throw JobErrorCode.CANNOT_CONVERT_CLOVA_RESPONSE.toException();
        }
    }

    private List<ExampleJobList> getExampleJobList(List<Job> jobs) {
        return jobs.stream()
                .map(ExampleJobList::from)
                .toList();
    }

    private String getContentFromResult(String result) {
        return clovaChatResponseMapper.jsonToChatResponse(result)
                .result()
                .message()
                .content();
    }
}
