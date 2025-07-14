package com.dodream.member.presentation.swagger;

import com.dodream.core.presentation.RestResponse;
import com.dodream.member.dto.request.EmailVerificationRequestDto;
import com.dodream.member.dto.request.VerificationEmailRequestDto;
import com.dodream.member.dto.response.EmailVerificationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberVerificationEmailSwagger {

    ResponseEntity<RestResponse<String>> sendVerificationEmail(
            @RequestBody VerificationEmailRequestDto verificationEmailRequestDto
    );

    ResponseEntity<RestResponse<EmailVerificationResponseDto>> sendVerificationEmail(
            @RequestBody EmailVerificationRequestDto verificationEmailRequestDto
    );
}
