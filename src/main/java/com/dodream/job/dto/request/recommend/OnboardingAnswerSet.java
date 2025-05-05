package com.dodream.job.dto.request.recommend;

import com.dodream.job.exception.JobErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record OnboardingAnswerSet(
        List<Answer> answers
) {
    public record Answer(
            @Schema(description = "질문 번호")
            int questionNum,
            List<String> responses
    ) {

        public Answer{
            if(questionNum < 1 || questionNum > 9){
                throw JobErrorCode.QUESTION_NUM_TOO_LARGE.toException();
            }
        }
    }
}