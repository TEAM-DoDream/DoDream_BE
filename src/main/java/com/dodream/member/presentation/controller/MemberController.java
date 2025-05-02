package com.dodream.member.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.member.application.MemberService;
import com.dodream.member.dto.response.UploadMemberProfileImageResponseDto;
import com.dodream.member.presentation.swagger.MemberSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
        @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(
            new RestResponse<>(memberService.uploadMemberProfileImage(file)));
    }

}
