package com.dodream.todo.dto.response;

import com.dodream.member.domain.Level;

import java.time.LocalDateTime;

public record TodoCommunityResponse(

        Long id,

        Long todoGroupId,

        String name,

        Level level,

        String imageUrl,

        LocalDateTime createdAt,

        String description,

        Long saveCount,

        boolean isSaved
) {
}
