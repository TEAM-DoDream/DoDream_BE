package com.dodream.member.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.dto.request.MemberLoginRequestDto;
import com.dodream.member.dto.request.MemberSignUpRequestDto;
import com.dodream.member.dto.response.MemberLoginResponseDto;
import com.dodream.member.dto.response.MemberSignUpResponseDto;
import com.dodream.member.presentation.swagger.MemberAuthSwagger;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/member/auth")
@RequiredArgsConstructor
@Validated
public class MemberAuthController implements MemberAuthSwagger {

    private final MemberAuthService memberAuthService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<RestResponse<MemberLoginResponseDto>> getMemberLogin(@RequestBody @Valid
    MemberLoginRequestDto memberLoginRequestDto) {
        return ResponseEntity.ok(
            new RestResponse<>(memberAuthService.getMemberLogin(memberLoginRequestDto)));
    }

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<RestResponse<MemberSignUpResponseDto>> getMemberSignUp(@RequestBody @Valid
    MemberSignUpRequestDto memberSignUpRequestDto) {
        return ResponseEntity.ok(
            new RestResponse<>(memberAuthService.getMemberSignUp(memberSignUpRequestDto)));
    }


}
