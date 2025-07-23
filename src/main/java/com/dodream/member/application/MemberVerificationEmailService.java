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

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class MemberVerificationEmailService {

    private final MemberRepository memberRepository;
    private final EmailUtil emailUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    private final SecureRandom secureRandom = new SecureRandom();

    private static final String CACHE_KEY_PREFIX = "email-code::sendVerificationCodeByEmail";

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
                    throw MemberErrorCode.EMAIL_NOT_FOUND.toException();
                }
            }
            case FIND_PASSWORD -> {
                if (!checkEmailAndLoginId(email, verificationEmailRequestDto.loginId())) {
                    throw MemberErrorCode.EMAIL_AND_LOGINID_NOT_FOUND.toException();
                }
            }
        }

        return true;
    }

    @CustomCacheable(cacheName = "email-code", ttl = 3)
    public String sendVerificationCodeByEmail(String email, VerificationType type) {
        String code = generateCode();
        emailUtil.sendVerificationEmail(email, type, code);

        // WARN: return 없을시 cache저장 안됨
        return code;
    }

    public EmailVerificationResponseDto authenticationCodeVerification(
            String email, VerificationType type, String code
    ) {

        String cacheKey = generateCacheKey(email, type);
        System.out.println(cacheKey);
        String cachedCode = (String) redisTemplate.opsForValue().get(cacheKey);
        System.out.println(cachedCode);
        if (cachedCode == null) {
            throw MemberErrorCode.VERIFICATION_NOT_FOUND.toException();
        }
        if (!code.equals(cachedCode)) {
            throw MemberErrorCode.CODE_NOT_MATCH.toException();
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
        return String.valueOf(secureRandom.nextInt(900_000) + 100_000);
    }

    private boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    private boolean checkEmailAndLoginId(String email, String loginId) {
        return memberRepository.existsByEmailAndLoginId(email, loginId);
    }

    private String generateCacheKey(String email, VerificationType type) {
        return CACHE_KEY_PREFIX + "(" + email + "," + type.name() + ")";
    }
}
