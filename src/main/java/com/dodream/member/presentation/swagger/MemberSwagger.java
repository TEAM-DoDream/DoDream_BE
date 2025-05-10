package com.dodream.member.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.member.dto.request.ChangeMemberBirthDateRequestDto;
import com.dodream.member.dto.request.ChangeMemberNickNameRequestDto;
import com.dodream.member.dto.request.ChangeMemberPasswordRequestDto;
import com.dodream.member.dto.request.ChangeMemberRegionRequestDto;
import com.dodream.member.dto.response.ChangeMemberBirthDateResponseDto;
import com.dodream.member.dto.response.ChangeMemberNickNameResponseDto;
import com.dodream.member.dto.response.ChangeMemberRegionResponseDto;
import com.dodream.member.dto.response.UploadMemberProfileImageResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "My account", description = "나의 계정 관련 API")
public interface MemberSwagger {

    @Operation(
        summary = "회원 프로필 사진 등록 및 변경 API",
        description = "사진을 등록하여 프로필 사진을 등록 및 변경한다.",
        operationId = "/v1/member/profile"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<UploadMemberProfileImageResponseDto>> uploadMemberProfileImage(
        @RequestParam("file") MultipartFile file);

    @Operation(
        summary = "비밀번호 변경 API",
        description = "새로운 비밀번호 & 비밀번호 확인을 입력하여 비밀번호를 변경한다.",
        operationId = "/v1/member/password"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<String>> changeMemberPassword(
        @RequestBody @Valid ChangeMemberPasswordRequestDto changeMemberPasswordRequestDto);

    @Operation(
        summary = "닉네임 변경 API",
        description = "새로운 닉네임으로 변경한다.",
        operationId = "/v1/member/nickname"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<ChangeMemberNickNameResponseDto>> changeMemberNickName(
        @RequestBody @Valid ChangeMemberNickNameRequestDto changeMemberNickNameRequestDto);

    @Operation(
        summary = "생년월일 변경 API",
        description = "새로운 생년월일로 변경한다.",
        operationId = "/v1/member/birth"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<ChangeMemberBirthDateResponseDto>> changeMemberBirth(
        @RequestBody @Valid ChangeMemberBirthDateRequestDto changeMemberBirthDateRequestDto);

    @Operation(
        summary = "거주지 변경 API",
        description = "새로운 거주지로 변경한다.",
        operationId = "/v1/member/region"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<ChangeMemberRegionResponseDto>> changeMemberRegion(
        @RequestBody @Valid ChangeMemberRegionRequestDto changeMemberRegionCodeRequestDto);


}
