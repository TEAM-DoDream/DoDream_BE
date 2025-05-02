package com.dodream.member.application;

import com.dodream.core.application.ObjectStorageService;
import com.dodream.core.config.security.SecurityUtils;
import com.dodream.member.domain.Member;
import com.dodream.member.dto.response.UploadMemberProfileImageResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ObjectStorageService objectStorageService;
    private final MemberRepository memberRepository;

    @Transactional
    public UploadMemberProfileImageResponseDto uploadMemberProfileImage(MultipartFile file) {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findById(currentId)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if (member.getProfileImage() != null) {
            objectStorageService.deleteMemberProfileImage(member.getProfileImage());
        }

        String createdImageUrl = objectStorageService.uploadMemberProfileImage(file, currentId);

        member.updateProfile(createdImageUrl);

        return UploadMemberProfileImageResponseDto.from(createdImageUrl);

    }

}
