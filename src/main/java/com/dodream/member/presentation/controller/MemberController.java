package com.dodream.member.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.member.application.MemberService;
import com.dodream.member.dto.request.ChangeMemberBirthDateRequestDto;
import com.dodream.member.dto.request.ChangeMemberNickNameRequestDto;
import com.dodream.member.dto.request.ChangeMemberPasswordRequestDto;
import com.dodream.member.dto.request.ChangeMemberRegionRequestDto;
import com.dodream.member.dto.response.ChangeMemberBirthDateResponseDto;
import com.dodream.member.dto.response.ChangeMemberNickNameResponseDto;
import com.dodream.member.dto.response.ChangeMemberRegionResponseDto;
import com.dodream.member.dto.response.UploadMemberProfileImageResponseDto;
import com.dodream.member.presentation.swagger.MemberSwagger;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
@Validated
public class MemberController implements MemberSwagger {

    private final MemberService memberService;

    @Override
    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestResponse<UploadMemberProfileImageResponseDto>> uploadMemberProfileImage(
        @Parameter(name = "file", description = "업로드할 프로필 이미지 (최대 10MB, 지원 형식: JPG, PNG..)", required = true)
        @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(
            new RestResponse<>(memberService.uploadMemberProfileImage(file)));
    }

    @Override
    @PutMapping("/password")
    public ResponseEntity<RestResponse<String>> changeMemberPassword(@RequestBody
    ChangeMemberPasswordRequestDto changeMemberPasswordRequestDto) {
        memberService.changeMemberPassword(changeMemberPasswordRequestDto);
        return ResponseEntity.ok(new RestResponse<>("비밀번호 변경 완료"));
    }

    @Override
    @PutMapping("/nickname")
    public ResponseEntity<RestResponse<ChangeMemberNickNameResponseDto>> changeMemberNickName(
        @RequestBody
        ChangeMemberNickNameRequestDto changeMemberNickNameRequestDto) {
        return ResponseEntity.ok(
            new RestResponse<>(memberService.changeMemberNickName(changeMemberNickNameRequestDto)));
    }

    @Override
    @PutMapping("/birth")
    public ResponseEntity<RestResponse<ChangeMemberBirthDateResponseDto>> changeMemberBirth(
        @RequestBody
        ChangeMemberBirthDateRequestDto changeMemberBirthDateRequestDto) {
        return ResponseEntity.ok(
            new RestResponse<>(memberService.changeMemberBirth(changeMemberBirthDateRequestDto)));
    }

    @Override
    @PutMapping("/region")
    public ResponseEntity<RestResponse<ChangeMemberRegionResponseDto>> changeMemberRegion(
        @RequestBody
        ChangeMemberRegionRequestDto changeMemberRegionCodeRequestDto) {
        return ResponseEntity.ok(
            new RestResponse<>(memberService.changeMemberRegion(changeMemberRegionCodeRequestDto)));
    }


}
