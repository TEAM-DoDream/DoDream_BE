package com.dodream.scrap.application.save;

import com.dodream.core.config.security.SecurityUtils;
import com.dodream.member.domain.Member;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import com.dodream.scrap.domain.value.TrainingType;
import com.dodream.scrap.dto.request.TrainingSaveReqeustDto;
import com.dodream.scrap.exception.ScrapErrorCode;
import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.scrap.dto.response.TrainingSavedResponseDto;
import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import com.dodream.scrap.repository.MemberTrainingScrapRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TrainingScrapService {

    private final TrainingMapper<TrainingDetailApiResponse> trainingDetailResponseDtoMapper;
    private final TrainingApiCaller bootcampApiCaller;
    private final TrainingApiCaller dualTrainingApiCaller;
    private final MemberRepository memberRepository;
    private final MemberTrainingScrapRepository memberTrainingScrapRepository;

    public TrainingScrapService(
            @Qualifier("trainingDetailResponseDtoMapper") TrainingMapper<TrainingDetailApiResponse> trainingDetailResponseDtoMapper,
            @Qualifier("bootCampApiCaller") TrainingApiCaller bootcampApiCaller,
            @Qualifier("dualTrainingApiCaller") TrainingApiCaller dualTrainingApiCaller,
            MemberRepository memberRepository,
            MemberTrainingScrapRepository memberTrainingScrapRepository
    ) {
        this.trainingDetailResponseDtoMapper = trainingDetailResponseDtoMapper;
        this.bootcampApiCaller = bootcampApiCaller;
        this.dualTrainingApiCaller = dualTrainingApiCaller;
        this.memberRepository = memberRepository;
        this.memberTrainingScrapRepository = memberTrainingScrapRepository;
    }

    @Transactional
    public TrainingSavedResponseDto saveTraining(TrainingSaveReqeustDto request, TrainingType trainingType) {
        Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
                .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if(memberTrainingScrapRepository.countByMemberId(member.getId()) > 50) {
            throw ScrapErrorCode.SCRAP_LIMIT_EXCEEDED.toException();
        }

        if(memberTrainingScrapRepository.existsByTrainingIdAndMemberId(request.trprId(), member.getId())){
            throw ScrapErrorCode.POST_IS_SAVED.toException();
        }

        TrainingDetailApiResponse response;
        if(trainingType.equals(TrainingType.BOOTCAMP)){
            response = trainingDetailResponseDtoMapper.jsonToResponseDto(
                    bootcampApiCaller.getDetailApi(
                            request.trprId(), request.trprDegr(), request.trainstCstId()
                    )
            );

        }else{
            response = trainingDetailResponseDtoMapper.jsonToResponseDto(
                    dualTrainingApiCaller.getDetailApi(
                            request.trprId(), request.trprDegr(), request.trainstCstId()
                    )
            );

        }
        memberTrainingScrapRepository.save(create(member, request, response));


        return new TrainingSavedResponseDto(
                member.getId(),
                true
        );
    }


    private MemberTrainingScrap create(
            Member member, TrainingSaveReqeustDto request, TrainingDetailApiResponse response
    ){
        return MemberTrainingScrap.of(
                member, request, response
        );
    }
}
