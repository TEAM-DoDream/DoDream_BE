package com.dodream.member.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.core.util.email.value.VerificationType;
import com.dodream.member.application.MemberVerificationEmailService;
import com.dodream.member.dto.request.EmailVerificationRequestDto;
import com.dodream.member.dto.request.VerificationEmailRequestDto;
import com.dodream.member.dto.response.EmailVerificationResponseDto;
import com.dodream.member.presentation.swagger.MemberVerificationEmailSwagger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/member/auth/email")
@RequiredArgsConstructor
public class MemberVerificationEmailController implements MemberVerificationEmailSwagger {

    private final MemberVerificationEmailService memberVerificationEmailService;

    @Override
    @PostMapping("/verifications")
    public ResponseEntity<RestResponse<String>> verifyEmailCode(
            @Valid @RequestBody VerificationEmailRequestDto verificationEmailRequestDto
    ) {
        memberVerificationEmailService.checkMemberEmail(verificationEmailRequestDto);
        memberVerificationEmailService.sendVerificationCodeByEmail(
                verificationEmailRequestDto.email(),
                verificationEmailRequestDto.type()
        );
        return ResponseEntity.ok(new RestResponse<>("이메일 전송에 성공했습니다."));
    }

    @Override
    @PostMapping("/verifications/verify")
    public ResponseEntity<RestResponse<EmailVerificationResponseDto>> sendVerificationEmail(
            @Valid @RequestBody EmailVerificationRequestDto verificationEmailRequestDto
    ) {
        return ResponseEntity.ok(new RestResponse<>(
                memberVerificationEmailService.authenticationCodeVerification(
                        verificationEmailRequestDto.email(),
                        verificationEmailRequestDto.type(),
                        verificationEmailRequestDto.code()
                )
        ));
    }


}
