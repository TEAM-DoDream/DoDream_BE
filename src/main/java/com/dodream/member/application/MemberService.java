package com.dodream.member.application;

import com.dodream.core.application.ObjectStorageService;
import com.dodream.core.config.security.SecurityUtils;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.member.dto.request.ChangeMemberBirthDateRequestDto;
import com.dodream.member.dto.request.ChangeMemberNickNameRequestDto;
import com.dodream.member.dto.request.ChangeMemberPasswordRequestDto;
import com.dodream.member.dto.request.ChangeMemberRegionRequestDto;
import com.dodream.member.dto.response.ChangeMemberBirthDateResponseDto;
import com.dodream.member.dto.response.ChangeMemberNickNameResponseDto;
import com.dodream.member.dto.response.ChangeMemberRegionResponseDto;
import com.dodream.member.dto.response.UploadMemberProfileImageResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import com.dodream.region.domain.Region;
import com.dodream.region.exception.RegionErrorCode;
import com.dodream.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ObjectStorageService objectStorageService;
    private final RegionRepository regionRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UploadMemberProfileImageResponseDto uploadMemberProfileImage(MultipartFile file) {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findByIdAndState(currentId, State.ACTIVE)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if (member.getProfileImage() != null) {
            objectStorageService.deleteMemberProfileImage(member.getProfileImage());
        }

        String createdImageUrl = objectStorageService.uploadMemberProfileImage(file, currentId);

        member.updateProfile(createdImageUrl);
        memberRepository.save(member);

        return UploadMemberProfileImageResponseDto.from(createdImageUrl);
    }

    @Transactional
    public void changeMemberPassword(ChangeMemberPasswordRequestDto requestDto) {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findByIdAndState(currentId, State.ACTIVE)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if (!requestDto.newPassword().equals(requestDto.newPasswordCheck())) {
            throw MemberErrorCode.PASSWORD_NOT_SAME.toException();
        }
        member.updatePassword(passwordEncoder.encode(requestDto.newPassword()));
        memberRepository.save(member);
    }

    @Transactional
    public ChangeMemberNickNameResponseDto changeMemberNickName(ChangeMemberNickNameRequestDto requestDto) {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findByIdAndState(currentId, State.ACTIVE)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if (memberRepository.existsByNickNameAndState(requestDto.newNickName(), State.ACTIVE)) {
            throw MemberErrorCode.DUPLICATE_NICKNAME.toException();
        }

        member.updateNickName(requestDto.newNickName());
        memberRepository.save(member);

        return ChangeMemberNickNameResponseDto.of(member.getId(), requestDto.newNickName());
    }

    @Transactional
    public ChangeMemberBirthDateResponseDto changeMemberBirth(ChangeMemberBirthDateRequestDto requestDto) {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findByIdAndState(currentId, State.ACTIVE)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        member.updateBirthDate(requestDto.newBirthDate());
        memberRepository.save(member);

        return ChangeMemberBirthDateResponseDto.of(member.getId(),requestDto.newBirthDate());
    }

    @Transactional
    public ChangeMemberRegionResponseDto changeMemberRegion(ChangeMemberRegionRequestDto requestDto) {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findByIdAndState(currentId, State.ACTIVE)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        Region newRegion = regionRepository.findByRegionCode(requestDto.newRegionCode())
                   .orElseThrow(RegionErrorCode.NOT_FOUND_REGION::toException);

        member.updateRegionCode(newRegion);
        memberRepository.save(member);

        return ChangeMemberRegionResponseDto.of(member.getId(), newRegion);
    }
}
