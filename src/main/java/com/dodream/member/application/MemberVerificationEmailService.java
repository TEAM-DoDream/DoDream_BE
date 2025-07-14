package com.dodream.member.application;

import com.dodream.core.infrastructure.cache.annotation.CustomCacheable;
import com.dodream.core.util.email.EmailUtil;
import com.dodream.core.util.email.value.VerificationType;
import com.dodream.member.dto.request.VerificationEmailRequestDto;
import com.dodream.member.dto.response.EmailVerificationResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberVerificationEmailService {

    private final MemberRepository memberRepository;
    private final EmailUtil emailUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    public boolean checkMemberEmail(VerificationEmailRequestDto verificationEmailRequestDto) {

        String email = verificationEmailRequestDto.email();
        VerificationType type = verificationEmailRequestDto.type();

        switch (type) {
            case SIGN_UP -> {
                if (checkEmail(email)) {
                    throw MemberErrorCode.DUPLICATE_EMAIL.toException();
                }
            }
            case FIND_ID -> {
                if (!checkEmail(email)) {
                    throw MemberErrorCode.NOT_FOUND_EMAIL.toException();
                }
            }
            case FIND_PASSWORD -> {
                if (!checkEmailAndLoginId(email, verificationEmailRequestDto.loginId())) {
                    throw MemberErrorCode.NOT_FOUND_EMAIL_AND_LOGINID.toException();
                }
            }
        }

        return true;
    }

    @CustomCacheable(cacheName = "email-code", ttl = 3)
    public String sendVerificationCodeByEmail(String email, VerificationType type) {

        String code = generateCode();
        emailUtil.sendVerificationEmail(email, type, code);
        return code;
    }

    public EmailVerificationResponseDto authenticationCodeVerification(
            String email, VerificationType type, String code
    ) {

        String cacheKey = "email-code::sendVerificationCodeByEmail(" + email + "," + type.name() + ")";

        String cachedCode = (String) redisTemplate.opsForValue().get(cacheKey);

        if (cachedCode == null) {
            throw MemberErrorCode.NOT_FOUND_VERIFICATION.toException();
        }
        if (!code.equals(cachedCode)) {
            throw MemberErrorCode.NOT_MATCHES_CODE.toException();
        }

        if(type.equals(VerificationType.FIND_ID)) {
            String loginId = memberRepository.findByEmail(email).orElseThrow(
                    MemberErrorCode.MEMBER_NOT_FOUND::toException
            ).getLoginId();

            return new EmailVerificationResponseDto(true, email, loginId);
        }else {
            return new EmailVerificationResponseDto(true, email, null);
        }
   }

    private String generateCode() {
        return String.valueOf((int)(Math.random() * 900_000) + 100_000);
    }

    private boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    private boolean checkEmailAndLoginId(String email, String loginId) {
        return memberRepository.existsByEmailAndLoginId(email, loginId);
    }
}
