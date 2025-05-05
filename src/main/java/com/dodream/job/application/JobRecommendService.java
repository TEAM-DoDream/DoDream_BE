package com.dodream.job.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.job.domain.Job;
import com.dodream.job.dto.request.recommend.ExampleJobList;
import com.dodream.job.dto.request.recommend.OnboardingAnswerSet;
import com.dodream.job.dto.response.ChatResponse;
import com.dodream.job.infrastructure.caller.ClovaChatCompletionCaller;
import com.dodream.job.infrastructure.factory.SystemPromptLoader;
import com.dodream.job.infrastructure.factory.UserPromptLoader;
import com.dodream.job.infrastructure.mapper.ClovaChatResponseMapper;
import com.dodream.job.repository.JobRepository;
import com.dodream.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobRecommendService {

    private final JobRepository jobRepository;
    private final ClovaChatCompletionCaller clovaChatCompletionCaller;
    private final MemberRepository memberRepository;
    private final ClovaChatResponseMapper clovaChatResponseMapper;

    public ChatResponse recommendJob(CustomUserDetails customUserDetails, OnboardingAnswerSet answerSet) {
        List<Job> jobs = jobRepository.findAll();

        List<ExampleJobList> exampleJobList = jobs.stream()
                .map(job -> ExampleJobList.from(job))
                .toList();

        String userName = customUserDetails.getUsername();

        String result = clovaChatCompletionCaller.clovaChatCompletionApiCaller(
                SystemPromptLoader.getPrompt(exampleJobList),
                UserPromptLoader.getPrompt(userName, answerSet)
        );

        return clovaChatResponseMapper.jsonToChatResponse(result);
    }
}
