package com.dodream.core.presentation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseResponse {

    private boolean success;

    private final LocalDateTime timestamp;
}
