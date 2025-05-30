package com.dodream.job.dto.response;

import java.util.List;

public record JobRecommendationResponse(
        List<RecommendedJob> recommendedJobs
) {
    public record RecommendedJob(
            String jobTitle,
            Long jobId,
            String jobDescription,
            String imageUrl,
            Reasons reasons
    ) {}

    public record Reasons(
            String personality,
            String strong,
            String condition
    ) {}
}
