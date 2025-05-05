package com.dodream.job.dto.request.recommend;

import java.util.List;

public record JobRecommendationResponse(
        String userName,
        List<RecommendedJob> recommendedJobs
) {
    public record RecommendedJob(
            String jobTitle,
            Reasons reasons
    ) {}

    public record Reasons(
            String personality,
            String strong,
            String condition
    ) {}
}
