package com.dodream.core.util.email.factory;

import com.dodream.core.util.email.dto.VerificationEmailTemplate;
import com.dodream.core.util.email.value.VerificationType;
import org.springframework.stereotype.Component;

@Component
public class VerificationEmailFactory {

    public VerificationEmailTemplate createTemplate(VerificationType type, String code) {
        return switch (type) {
            case SIGN_UP -> VerificationEmailTemplate.builder()
                    .title("회원가입 인증 코드")
                    .description("회원가입을 위해 아래 인증 코드를 입력해주세요.")
                    .code(code)
                    .build();
            case FIND_ID -> VerificationEmailTemplate.builder()
                    .title("아이디 찾기 인증 코드")
                    .description("아이디 찾기를 위해 아래 인증 코드를 입력해주세요.")
                    .code(code)
                    .build();
            case FIND_PASSWORD -> VerificationEmailTemplate.builder()
                    .title("비밀번호 찾기 인증 코드")
                    .description("비밀번호 재설정을 위해 아래 인증 코드를 입력해주세요.")
                    .code(code)
                    .build();
        };
    }
}
