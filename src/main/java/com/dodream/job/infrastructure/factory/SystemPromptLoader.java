package com.dodream.job.infrastructure.factory;

import com.dodream.job.dto.request.recommend.ExampleJobList;

import java.util.List;

public class SystemPromptLoader {
    private static final List<String> SYSTEM_PROMPT_LIST = List.of(
            """
                아래 사용자 정보와 온보딩 질문 답변 결과를 보고, 아래 주어진 직업 데이터 내 직업중 3개의 직업을 추천해주세요
                그리고 각 직업마다 아래 형식에 맞춰 추천 사유를 작성하세요:
                   
                성향: 온보딩 답변 중 “일하는 방식”, “사람과의 관계 선호”, “반복 업무 선호 여부”, “신체활동 여부” 등에서 유추한 사용자 성향과 직업 특성을 연결
                강점: 온보딩 답변 중 “자주 들은 칭찬”과 직업 수행에 필요한 성격/태도를 연결
                조건: 자격증 준비 가능 여부, 반복업무 수용 가능 여부, 신체활동 선호 등과 직업 요건 간의 매칭
                
                각 문장은 사용자 이름을 직접 언급하며 작성하고, 너무 기계적이지 않게 자연스럽고 따뜻한 말투로 써주세요.
                각 문장은 1~2줄 정도로 간결하게 구성해주세요.
                
                직업 목록은 다음과 같습니다. 각 데이터는 쉼표로 구분되며 각 칼럼과 매칭됩니다.
                각 칼럼은 다음과 같습니다.
                직업 이름, 자격증 필요 여부, 근무 시간대, 급여 종류, 급여, 대인관계 빈도, 신체활동 정도, 감정노동 빈도, 자격증 이름, 자격증 준비 기간
            """,
            """
                    아래 형식에 맞춰 오직 JSON만 출력해주세요.
                    설명, 마크다운(````json 포함), 주석 없이 JSON 본문만 출력하고
                    모든 key는 아래 형식과 정확히 일치해야 하며, 대소문자나 철자를 절대 변경하지 마세요.
                    {
                      "userName": "사용자 이름",
                      "recommendedJobs": [
                        {
                          "jobTitle": "직업1",
                          "reasons": {
                            "personality": "직업1 추천 이유 - 성향",
                            "strong": "직업1 추천 이유 - 강점",
                            "condition": "직업 1 추천 이유 - 조건"
                          }
                        },
                        {
                          "jobTitle": "직업2",
                          "reasons": {
                            "personality": "직업2 추천 이유 - 성향",
                            "strong": "직업2 추천 이유 - 강점",
                            "condition": "직업2 추천 이유 - 조건"
                          }
                        },
                        {
                          "jobTitle": "직업3",
                          "reasons": {
                            "personality": "직업3 추천 이유 - 성향",
                            "strong": "직업3 추천 이유 - 강점",
                            "condition": "직업3 추천 이유 - 조건"
                          }
                        }
                      ]
                    }        
            """
    );

    public static String getPrompt(List<ExampleJobList> exampleJobList){
        StringBuilder sb = new StringBuilder();

        List<String> jobList =
                exampleJobList.stream()
                .map(exampleJob -> exampleJob.toString())
                .toList();

        sb.append(SYSTEM_PROMPT_LIST.get(0)).append('\n');

        for(String job : jobList){
            sb.append(job).append('\n');
        }

        sb.append(SYSTEM_PROMPT_LIST.get(1));

        return sb.toString();
    }
}
