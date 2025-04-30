package com.dodream.member.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.member.dto.request.MemberLoginRequestDto;
import com.dodream.member.dto.request.MemberSignUpRequestDto;
import com.dodream.member.dto.response.CheckMemberIdResponseDto;
import com.dodream.member.dto.response.CheckMemberNickNameResponseDto;
import com.dodream.member.dto.response.MemberLoginResponseDto;
import com.dodream.member.dto.response.MemberSignUpResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Member Auth", description = "회원 인증 관련 API")
public interface MemberAuthSwagger {

    @Operation(
        summary = "회원 로그인 API",
        description = "아이디와 비번 입력하여 로그인 진행",
        operationId = "/v1/member/auth/login"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<MemberLoginResponseDto>> getMemberLogin(
        @RequestBody MemberLoginRequestDto memberLoginRequestDto);

    @Operation(
        summary = "회원 회원가입 API",
        description = "아이디,비밀번호,닉네임,성별 등을 입력하여 회원가입 진행",
        operationId = "/v1/member/auth/sign-up"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<MemberSignUpResponseDto>> getMemberSignUp(
        @RequestBody MemberSignUpRequestDto memberSignUpRequestDto);

    @Operation(
        summary = "회원 회원가입 - 아이디 중복 확인 API",
        description = "아이디의 중복 확인 여부를 확인",
        operationId = "/v1/member/auth/check-id/{memberId}"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<CheckMemberIdResponseDto>> checkMemberId(
        @Parameter(name = "memberId", description = "가입하려는 아이디", example = "dodream")
        @PathVariable String memberId
    );

    @Operation(
        summary = "회원 회원가입 - 닉네임 중복 확인 API",
        description = "닉네임 중복 확인 여부를 확인",
        operationId = "/v1/member/auth/check-nickname/{nickname}"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<CheckMemberNickNameResponseDto>> checkMemberNickName(
        @Parameter(name = "nickname", description = "가입하려는 닉네임", example = "두둠칫")
        @PathVariable String nickname
    );


}
