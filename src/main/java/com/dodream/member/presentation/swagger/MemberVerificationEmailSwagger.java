package com.dodream.member.presentation.swagger;

import com.dodream.core.presentation.RestResponse;
import com.dodream.member.dto.request.VerificationEmailRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberVerificationEmailSwagger {

    ResponseEntity<RestResponse<String>> sendVerificationEmail(
            @RequestBody VerificationEmailRequestDto verificationEmailRequestDto
    );
}
