package com.dodream.member.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.dto.request.MemberLoginRequestDto;
import com.dodream.member.dto.request.MemberSignUpRequestDto;
import com.dodream.member.dto.response.CheckMemberIdResponseDto;
import com.dodream.member.dto.response.CheckMemberNickNameResponseDto;
import com.dodream.member.dto.response.MemberLoginResponseDto;
import com.dodream.member.dto.response.MemberNewTokenResponse;
import com.dodream.member.dto.response.MemberSignUpResponseDto;
import com.dodream.member.presentation.swagger.MemberAuthSwagger;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @PostMapping("/logout")
    public ResponseEntity<RestResponse<String>> getMemberLogout() {
        memberAuthService.getMemberLogout();
        return ResponseEntity.ok(new RestResponse<>("로그아웃 완료"));
    }

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<RestResponse<MemberSignUpResponseDto>> getMemberSignUp(@RequestBody @Valid
    MemberSignUpRequestDto memberSignUpRequestDto) {
        return ResponseEntity.ok(
            new RestResponse<>(memberAuthService.getMemberSignUp(memberSignUpRequestDto)));
    }

    @Override
    @GetMapping("/check-id/{memberId}")
    public ResponseEntity<RestResponse<CheckMemberIdResponseDto>> checkMemberId(
        @Parameter(name = "memberId", description = "가입하려는 아이디", example = "dodream")
        @PathVariable String memberId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberAuthService.checkDuplicateMemberId(memberId)));
    }

    @Override
    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity<RestResponse<CheckMemberNickNameResponseDto>> checkMemberNickName(
        @Parameter(name = "nickname", description = "가입하려는 닉네임", example = "두둠칫")
        @PathVariable String nickname) {
        return ResponseEntity.ok(
            new RestResponse<>(memberAuthService.checkDuplicateMemberNickName(nickname)));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<RestResponse<MemberNewTokenResponse>> issueNewToken(
        @RequestHeader(value = "refreshToken", required = false) String refreshToken) {
        return ResponseEntity.ok(
            new RestResponse<>(memberAuthService.issueNewToken(refreshToken)));
    }


}
