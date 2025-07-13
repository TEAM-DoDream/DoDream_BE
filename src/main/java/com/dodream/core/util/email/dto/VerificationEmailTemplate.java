package com.dodream.core.util.email.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerificationEmailTemplate {
    private String title;
    private String description;
    private String code;
}
