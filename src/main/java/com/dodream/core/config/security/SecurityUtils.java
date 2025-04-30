package com.dodream.core.config.security;

import com.dodream.auth.exception.AuthenticationErrorCode;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
            || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw AuthenticationErrorCode.NOT_AUTHENTICATED.toException();
        }
        return userDetails.getId();
    }

}
