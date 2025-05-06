package com.dodream.job.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.job.domain.Job;
import com.dodream.job.dto.request.recommend.ExampleJobList;
import com.dodream.job.dto.response.JobRecommendationResponse;
import com.dodream.job.dto.request.recommend.OnboardingAnswerSet;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.infrastructure.caller.ClovaChatCompletionCaller;
import com.dodream.job.infrastructure.caller.JobApiCaller;
import com.dodream.job.infrastructure.factory.SystemPromptLoader;
import com.dodream.job.infrastructure.factory.UserPromptLoader;
import com.dodream.job.infrastructure.mapper.ClovaChatResponseMapper;
import com.dodream.job.infrastructure.mapper.JobDescriptionMapper;
import com.dodream.job.infrastructure.mapper.JobRecommendMapper;
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
    private final JobRecommendMapper jobRecommendMapper;
    private final JobApiCaller jobApiCaller;
    private final JobDescriptionMapper jobDescriptionMapper;

    public JobRecommendationResponse recommendJob(CustomUserDetails customUserDetails, OnboardingAnswerSet answerSet) {
        String result = clovaChatCompletionCaller.clovaChatCompletionApiCaller(
                SystemPromptLoader.getPrompt(getExampleJobList(jobRepository.findAll())),
                UserPromptLoader.getPrompt(customUserDetails.getUsername(), answerSet)
        );

        if(result == null || result.isEmpty()) {
            throw JobErrorCode.CANNOT_CONVERT_CLOVA_RESPONSE.toException();
        }

        JobRecommendationResponse jobRecommendationResponse
                = jobRecommendMapper.parse(JsonCleaner.cleanJson(getContentFromResult(result)));

        List<JobRecommendationResponse.RecommendedJob> updatedJobs = jobRecommendationResponse.recommendedJobs().stream()
                .map(job -> new JobRecommendationResponse.RecommendedJob(
                        job.jobTitle(),
                        jobDescriptionMapper.toJobDescriptionDto(
                                jobApiCaller.jobDesriptionApiCaller(
                                        jobRepository.findByJobName(job.jobTitle()).orElseThrow(
                                                JobErrorCode.CANNOT_GET_JOB_DATA::toException
                                        ).getJobCode()
                                )
                        ).jobSum(),
                        job.reasons()
                ))
                .toList();

        return new JobRecommendationResponse(updatedJobs);
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
