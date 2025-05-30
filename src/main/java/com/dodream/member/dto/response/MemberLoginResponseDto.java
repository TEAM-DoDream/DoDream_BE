package com.dodream.member.dto.response;

import com.dodream.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record MemberLoginResponseDto(
    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,
    @Schema(description = "로그인 아이디", example = "dodream")
    String loginId,
    @Schema(description = "닉네임", example = "두둠칫")
    String nickname,
    @Schema(description = "생년월일", example = "2001/01/05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    LocalDate birthDate,
    @Schema(description = "성벌", example = "여자")
    String gender,
    @Schema(description = "프로필", example = "www.kr.sdfsd.com/member/1/image.png")
    String profileImage,
    @Schema(description = "지역코드", example = "11100")
    String regionCode,
    @Schema(description = "지역이름", example = "서울시 강동구")
    String regionName,
    @Schema(description = "access 토큰", example = "abcaccess1232131")
    String accessToken,
    @Schema(description = "refresh 토큰", example = "dferefresh1243124")
    String refreshToken
) {

    public static MemberLoginResponseDto of(Member member, String accessToken,
        String refreshToken) {
        return MemberLoginResponseDto.builder()
            .memberId(member.getId())
            .loginId(member.getLoginId())
            .nickname(member.getNickName())
            .birthDate(member.getBirthDate())
            .gender(member.getGender().getValue())
            .profileImage(member.getProfileImage())
            .regionCode(member.getRegion() != null ? member.getRegion().getRegionCode() : null)
            .regionName(member.getRegion() != null ? member.getRegion().getRegionName() : null)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

}
