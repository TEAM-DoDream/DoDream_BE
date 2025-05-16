package com.dodream.job.dto.request.clova;

import java.util.List;

public record ClovaMessage(
        String role,

        String content
) {

    public static ClovaMessage createSystemMessage(String content) {
        return new ClovaMessage(
                "system",
                content
        );
    }

    public static ClovaMessage createUserMessage(String content) {
        return new ClovaMessage(
                "user",
                content
        );
    }
}
