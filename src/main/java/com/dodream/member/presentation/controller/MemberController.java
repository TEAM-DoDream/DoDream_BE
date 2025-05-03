package com.dodream.member.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.member.application.MemberService;
import com.dodream.member.dto.request.ChangeMemberBirthDateRequestDto;
import com.dodream.member.dto.request.ChangeMemberNickNameRequestDto;
import com.dodream.member.dto.request.ChangeMemberPasswordRequestDto;
import com.dodream.member.dto.request.ChangeMemberRegionCodeRequestDto;
import com.dodream.member.dto.response.UploadMemberProfileImageResponseDto;
import com.dodream.member.presentation.swagger.MemberSwagger;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<RestResponse<String>> changeMemberPassword(@RequestBody @Valid
    ChangeMemberPasswordRequestDto changeMemberPasswordRequestDto) {
        memberService.changeMemberPassword(changeMemberPasswordRequestDto);
        return ResponseEntity.ok(new RestResponse<>("비밀번호 변경 완료"));
    }

    @Override
    @PutMapping("/nickname")
    public ResponseEntity<RestResponse<String>> changeMemberNickName(@RequestBody @Valid
    ChangeMemberNickNameRequestDto changeMemberNickNameRequestDto) {
        memberService.changeMemberNickName(changeMemberNickNameRequestDto);
        return ResponseEntity.ok(new RestResponse<>("닉네임 변경 완료"));
    }

    @Override
    @PutMapping("/birth")
    public ResponseEntity<RestResponse<String>> changeMemberBirth(@RequestBody @Valid
    ChangeMemberBirthDateRequestDto changeMemberBirthDateRequestDto) {
        memberService.changeMemberBirth(changeMemberBirthDateRequestDto);
        return ResponseEntity.ok(new RestResponse<>("생년월일 변경 완료"));
    }

    @Override
    @PutMapping("/region")
    public ResponseEntity<RestResponse<String>> changeMemberRegion(@RequestBody @Valid
    ChangeMemberRegionCodeRequestDto changeMemberRegionCodeRequestDto) {
        memberService.changeMemberRegion(changeMemberRegionCodeRequestDto);
        return ResponseEntity.ok(new RestResponse<>("거주지 변경 완료"));
    }


}
