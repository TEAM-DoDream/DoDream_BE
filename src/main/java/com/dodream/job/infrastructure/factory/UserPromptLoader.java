package com.dodream.job.infrastructure.factory;

import com.dodream.job.dto.request.recommend.OnboardingAnswerSet;

import java.util.List;

public class UserPromptLoader {

    private static final String MESSAGE_HEADER_SUFFIX = "님의 온보딩 질문 답변 결과입니다.";

    public static List<String> USER_PROMPTS = List.of(
            "1. 최근에 \"재밌다\"거나 \"잘할 수 있겠다\"는 생각이 든 순간은 언제였나요?",
            "2. 시간이 가는 줄 모르고 몰입하셨던 순간은 언제였나요?",
            "3. 다른 사람에게 자주 들었던 칭찬은 무엇인가요?",
            "4. 직업을 선택하실 때 가장 중요하게 생각하는 건 무엇인가요?",
            "5. 언제부터 일을 시작하고 싶으신가요?",
            "6. 어떤 방식으로 일하실 때 가장 편하신가요?",
            "7. 매일 같은 일을 반복하는 것도 괜찮으신가요?",
            "8. 몸을 많이 쓰는 일도 괜찮으신가요?",
            "9. 자격증이 필요한 일도 괜찮으신가요?"
    );

    public static String getPrompt(String memberName, OnboardingAnswerSet answerSet) {
        List<OnboardingAnswerSet.Answer> answers = answerSet.answers();

        StringBuilder sb = new StringBuilder();
        sb.append(memberName).append(MESSAGE_HEADER_SUFFIX).append("\n");

        for(OnboardingAnswerSet.Answer answer : answers) {
            sb.append(USER_PROMPTS.get(answer.questionNum() - 1)).append("\n");
            sb.append("답변:").append(String.join(",", answer.responses())).append("\n");
        }

        return sb.toString();
    }
}
