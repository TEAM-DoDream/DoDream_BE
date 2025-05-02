package com.dodream.member.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.member.dto.response.UploadMemberProfileImageResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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


}
