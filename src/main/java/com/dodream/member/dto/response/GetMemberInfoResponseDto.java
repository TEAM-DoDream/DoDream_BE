package com.dodream.member.dto.response;

import com.dodream.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record GetMemberInfoResponseDto(

    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,
    @Schema(description = "프로필", example = "www.kr.sdfsd.com/member/1/image.png")
    String profileImage,
    @Schema(description = "닉네임", example = "두둠칫")
    String nickname,
    @Schema(description = "로그인 아이디", example = "dodream")
    String loginId,
    @Schema(description = "생년월일", example = "2001/01/05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    LocalDate birthDate,
    @Schema(description = "거주지", example = "서울시 강동구")
    String regionName,
    @Schema(description = "관심 직업")
    GetMemberInterestedJobResponseDto job,
    @Schema(description = "레벨")
    String level
) {

    public static GetMemberInfoResponseDto of(Member member,
        GetMemberInterestedJobResponseDto job) {
        return GetMemberInfoResponseDto.builder()
            .memberId(member.getId())
            .profileImage(member.getProfileImage())
            .nickname(member.getNickName())
            .loginId(member.getLoginId())
            .birthDate(member.getBirthDate())
            .regionName(member.getRegion().getRegionName())
            .job(job)
            .level(member.getLevel() != null ? member.getLevel().getValue() : null)
            .build();
    }
}