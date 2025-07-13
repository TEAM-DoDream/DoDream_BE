package com.dodream.member.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.member.application.MemberVerificationEmailService;
import com.dodream.member.dto.request.VerificationEmailRequestDto;
import com.dodream.member.presentation.swagger.MemberVerificationEmailSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/member/auth/email")
@RequiredArgsConstructor
public class MemberVerificationEmailController implements MemberVerificationEmailSwagger {

    private final MemberVerificationEmailService memberVerificationEmailService;

    @Override
    @PostMapping("/send")
    public ResponseEntity<RestResponse<String>> sendVerificationEmail(VerificationEmailRequestDto verificationEmailRequestDto) {
        if(memberVerificationEmailService.checkMemberEmail(verificationEmailRequestDto)) {
            memberVerificationEmailService.sendVeificationCodeByEmail(
                    verificationEmailRequestDto.email(),
                    verificationEmailRequestDto.type()
            );
        }
        return ResponseEntity.ok(new RestResponse<>("이메일 전송에 성공했습니다."));
    }
}
