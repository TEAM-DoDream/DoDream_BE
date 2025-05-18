package com.dodream.member.application;

import com.dodream.core.application.ObjectStorageService;
import com.dodream.core.config.security.SecurityUtils;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.member.dto.request.ChangeMemberBirthDateRequestDto;
import com.dodream.member.dto.request.ChangeMemberJobsRequestDto;
import com.dodream.member.dto.request.ChangeMemberNickNameRequestDto;
import com.dodream.member.dto.request.ChangeMemberPasswordRequestDto;
import com.dodream.member.dto.request.ChangeMemberRegionRequestDto;
import com.dodream.member.dto.response.ChangeMemberBirthDateResponseDto;
import com.dodream.member.dto.response.ChangeMemberNickNameResponseDto;
import com.dodream.member.dto.response.ChangeMemberRegionResponseDto;
import com.dodream.member.dto.response.GetMemberInfoResponseDto;
import com.dodream.member.dto.response.GetMemberInterestedJobResponseDto;
import com.dodream.member.dto.response.UploadMemberProfileImageResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import com.dodream.region.domain.Region;
import com.dodream.region.exception.RegionErrorCode;
import com.dodream.region.repository.RegionRepository;
import com.dodream.todo.application.TodoMemberService;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.dto.response.DeleteTodoGroupResponseDto;
import com.dodream.todo.repository.TodoGroupRepository;
import java.util.List;
import java.util.stream.Collectors;
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
    private final TodoGroupRepository todoGroupRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberAuthService memberAuthService;
    private final TodoMemberService todoMemberService;


    @Transactional
    public UploadMemberProfileImageResponseDto uploadMemberProfileImage(MultipartFile file) {

        Member member = memberAuthService.getCurrentMember();

        if (member.getProfileImage() != null) {
            objectStorageService.deleteMemberProfileImage(member.getProfileImage());
        }

        String createdImageUrl = objectStorageService.uploadMemberProfileImage(file,
            member.getId());

        member.updateProfile(createdImageUrl);
        memberRepository.save(member);

        return UploadMemberProfileImageResponseDto.from(createdImageUrl);
    }

    @Transactional
    public void changeMemberPassword(ChangeMemberPasswordRequestDto requestDto) {

        Member member = memberAuthService.getCurrentMember();

        if (!requestDto.newPassword().equals(requestDto.newPasswordCheck())) {
            throw MemberErrorCode.PASSWORD_NOT_SAME.toException();
        }
        member.updatePassword(passwordEncoder.encode(requestDto.newPassword()));
        memberRepository.save(member);
    }

    @Transactional
    public ChangeMemberNickNameResponseDto changeMemberNickName(
        ChangeMemberNickNameRequestDto requestDto) {

        Member member = memberAuthService.getCurrentMember();

        if (memberRepository.existsByNickNameAndState(requestDto.newNickName(), State.ACTIVE)) {
            throw MemberErrorCode.DUPLICATE_NICKNAME.toException();
        }

        member.updateNickName(requestDto.newNickName());
        memberRepository.save(member);

        return ChangeMemberNickNameResponseDto.of(member.getId(), requestDto.newNickName());
    }

    @Transactional
    public ChangeMemberBirthDateResponseDto changeMemberBirth(
        ChangeMemberBirthDateRequestDto requestDto) {

        Member member = memberAuthService.getCurrentMember();

        member.updateBirthDate(requestDto.newBirthDate());
        memberRepository.save(member);

        return ChangeMemberBirthDateResponseDto.of(member.getId(), requestDto.newBirthDate());
    }

    @Transactional
    public ChangeMemberRegionResponseDto changeMemberRegion(
        ChangeMemberRegionRequestDto requestDto) {

        Member member = memberAuthService.getCurrentMember();

        Region newRegion = regionRepository.findByRegionCode(requestDto.newRegionCode())
            .orElseThrow(RegionErrorCode.NOT_FOUND_REGION::toException);

        member.updateRegionCode(newRegion);
        memberRepository.save(member);

        return ChangeMemberRegionResponseDto.of(member.getId(), newRegion);
    }

    @Transactional(readOnly = true)
    public GetMemberInfoResponseDto getMemberInfo() {

        Member member = memberAuthService.getCurrentMember();

        List<GetMemberInterestedJobResponseDto> jobs = todoGroupRepository.findAllByMember(member)
            .stream()
            .map(todoGroup -> GetMemberInterestedJobResponseDto.from(todoGroup.getJob()))
            .toList();

        return GetMemberInfoResponseDto.of(member,jobs);

    }

    public DeleteTodoGroupResponseDto deleteInterestedJobs(ChangeMemberJobsRequestDto requestDto){

        Member member = memberAuthService.getCurrentMember();

        return todoMemberService.deleteTodoGroups(requestDto.jobIds(),member);

    }
}
