package com.dodream.job.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.job.domain.Job;
import com.dodream.job.dto.request.recommend.ExampleJobList;
import com.dodream.job.dto.response.JobRecommendationResponse;
import com.dodream.job.dto.request.recommend.OnboardingAnswerSet;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.infrastructure.JobImageUrlGenerator;
import com.dodream.job.infrastructure.caller.ClovaChatCompletionCaller;
import com.dodream.job.infrastructure.factory.SystemPromptLoader;
import com.dodream.job.infrastructure.factory.UserPromptLoader;
import com.dodream.job.infrastructure.mapper.ClovaChatResponseMapper;
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
    private final JobImageUrlGenerator imageUrlGenerator;

    public JobRecommendationResponse recommendJob(CustomUserDetails customUserDetails, OnboardingAnswerSet answerSet) {
        if(customUserDetails == null) {
            throw JobErrorCode.CANNOT_FIND_USER_DATA.toException();
        }

        String rawResponse = callClovaApi(customUserDetails.getUsername(), answerSet);

        JobRecommendationResponse rawRecommendation = parseRecommendationResponse(rawResponse);
        List<JobRecommendationResponse.RecommendedJob> enrichedJobs
                = enrichRecommendedJobs(rawRecommendation.recommendedJobs());

        return new JobRecommendationResponse(enrichedJobs);
    }

    private List<ExampleJobList> getExampleJobList(List<Job> jobs) {
        return jobs.stream()
                .map(ExampleJobList::from)
                .toList();
    }

    private String callClovaApi(String username, OnboardingAnswerSet answerSet) {
        String systemPrompt = SystemPromptLoader.getPrompt(getExampleJobList(jobRepository.findAll()));
        String userPrompt = UserPromptLoader.getPrompt(username, answerSet);
        String result = clovaChatCompletionCaller.clovaChatCompletionApiCaller(systemPrompt, userPrompt);

        if (result == null || result.isEmpty()) {
            throw JobErrorCode.CANNOT_CONVERT_CLOVA_RESPONSE.toException();
        }

        return result;
    }

    private List<JobRecommendationResponse.RecommendedJob> enrichRecommendedJobs(
            List<JobRecommendationResponse.RecommendedJob> rawJobs
    ) {
        return rawJobs.stream()
                .map(this::enrichJobWithDescription)
                .toList();
    }

    private JobRecommendationResponse.RecommendedJob enrichJobWithDescription(JobRecommendationResponse.RecommendedJob job) {
        Job result = jobRepository.findByJobName(job.jobTitle())
                .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);

        return new JobRecommendationResponse.RecommendedJob(
                job.jobTitle(),
                result.getId(),
                result.getJobSummary(),
                imageUrlGenerator.getImageUrl(result.getId()),
                job.reasons()
        );
    }

    private JobRecommendationResponse parseRecommendationResponse(String rawResponse) {
        String cleanedJson = JsonCleaner.cleanJson(getContentFromResult(rawResponse));
        return jobRecommendMapper.parse(cleanedJson);
    }

    private String getContentFromResult(String result) {
        return clovaChatResponseMapper.jsonToChatResponse(result)
                .result()
                .message()
                .content();
    }

}
