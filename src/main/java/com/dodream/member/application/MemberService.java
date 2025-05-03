package com.dodream.member.application;

import com.dodream.core.application.ObjectStorageService;
import com.dodream.core.config.security.SecurityUtils;
import com.dodream.member.domain.Member;
import com.dodream.member.dto.request.ChangeMemberBirthDateRequestDto;
import com.dodream.member.dto.request.ChangeMemberNickNameRequestDto;
import com.dodream.member.dto.request.ChangeMemberPasswordRequestDto;
import com.dodream.member.dto.request.ChangeMemberRegionCodeRequestDto;
import com.dodream.member.dto.response.UploadMemberProfileImageResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ObjectStorageService objectStorageService;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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

    @Transactional
    public void changeMemberPassword(ChangeMemberPasswordRequestDto requestDto) {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findById(currentId)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if (!requestDto.newPassword().equals(requestDto.newPasswordCheck())) {
            throw MemberErrorCode.PASSWORD_NOT_SAME.toException();
        }
        member.updatePassword(passwordEncoder.encode(requestDto.newPassword()));
    }

    @Transactional
    public void changeMemberNickName(ChangeMemberNickNameRequestDto requestDto) {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findById(currentId)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if (memberRepository.existsByNickName(requestDto.newNickName())){
            throw MemberErrorCode.DUPLICATE_NICKNAME.toException();
        }

        member.updateNickName(requestDto.newNickName());
    }

    @Transactional
    public void changeMemberBirth(ChangeMemberBirthDateRequestDto requestDto) {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findById(currentId)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        member.updateBirthDate(requestDto.newBirthDate());
    }

    @Transactional
    public void changeMemberRegion(ChangeMemberRegionCodeRequestDto requestDto) {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findById(currentId)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        member.updateRegionCode(requestDto.newRegionCode());
    }


}
