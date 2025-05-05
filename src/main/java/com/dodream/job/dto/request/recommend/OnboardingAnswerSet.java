package com.dodream.job.dto.request.recommend;

import java.util.List;

public record OnboardingAnswerSet(
        List<Answer> answers
) {
    public record Answer(
            String questionNum,
            List<String> responses
    ) {}
}