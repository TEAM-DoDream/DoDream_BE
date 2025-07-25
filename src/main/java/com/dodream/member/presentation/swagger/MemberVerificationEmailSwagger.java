package com.dodream.member.presentation.swagger;

import com.dodream.core.presentation.RestResponse;
import com.dodream.member.dto.request.ChangeMemberPasswordByEmailRequestDto;
import com.dodream.member.dto.request.EmailVerificationRequestDto;
import com.dodream.member.dto.request.VerificationEmailRequestDto;
import com.dodream.member.dto.response.EmailVerificationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name="Email-Auth", description = "이메일 인증 관련 API 모음")
public interface MemberVerificationEmailSwagger {

    @Operation(
            summary = "이메일 인증 요청 API",
            description = "이메일 인증 요청 API, 실행시 입력한 이메일로 인증 코드가 전송됩니다.<br>" +
                    "type = (SIGN_UP, FIND_ID, FIND_PASSWORD)<br>" +
                    "FIND_PASSWORD시에만 loginId 값 입력 필요함",
            operationId = "/v1/member/auth/email/verifications"
    )
    ResponseEntity<RestResponse<String>> verifyEmailCode(
            @Valid @RequestBody VerificationEmailRequestDto verificationEmailRequestDto
    );

    @Operation(
            summary = "이메일 인증 코드 확인 API",
            description = "이메일을 통해 전달받은 코드가 일치하는지 확인하는 API<br>"+
                    "type = (SIGN_UP, FIND_ID, FIND_PASSWORD)",
            operationId = "/v1/member/auth/email/verifications/verify"
    )
    ResponseEntity<RestResponse<EmailVerificationResponseDto>> sendVerificationEmail(
            @Valid @RequestBody EmailVerificationRequestDto verificationEmailRequestDto
    );

    @Operation(
            summary = "이메일 인증 후 비밀번호 변경 api",
            description = "이메일 인증 이후 비밀번호를 변경할 때 사용하는 API",
            operationId = "/v1/member/auth/email/password"
    )
    ResponseEntity<RestResponse<Boolean>> changePassword(
            @Valid @RequestBody ChangeMemberPasswordByEmailRequestDto changeMemberPasswordByEmailRequestDto
    );
}
