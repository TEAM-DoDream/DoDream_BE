package com.dodream.job.dto.response;

public record ChatResponse(
//        Status status,
        Result result
) {
//    public record Status(
//            String code,
//            String message
//    ) {}

    public record Result(
            Message message,
            String finishReason,
//            long created,
//            long seed,
            Usage usage
    ) {}

    public record Message(
            String role,
            String content
    ) {}

    public record Usage(
            int promptTokens,
            int completionTokens,
            int totalTokens
    ) {}
}
