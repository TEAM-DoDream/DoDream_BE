package com.dodream.training.application;

import com.dodream.core.config.security.SecurityUtils;
import com.dodream.member.domain.Member;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import com.dodream.training.domain.MemberTrainingScrap;
import com.dodream.training.domain.TrainingType;
import com.dodream.training.dto.request.TrainingSaveReqeustDto;
import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.scrap.TrainingScrapResponseDto;
import com.dodream.training.exception.TrainingErrorCode;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import com.dodream.training.repository.MemberTrainingScrapRepository;
import com.dodream.training.util.executer.TrainingApiExecuter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TrainingScrapService {

    private final TrainingMapper<TrainingDetailApiResponse> trainingDetailResponseDtoMapper;
    private final TrainingApiExecuter bootcampTrainingApiExecuter;
    private final TrainingApiExecuter dualTrainingApiExecuter;
    private final MemberRepository memberRepository;
    private final MemberTrainingScrapRepository memberTrainingScrapRepository;

    public TrainingScrapService(
            @Qualifier("trainingDetailResponseDtoMapper") TrainingMapper<TrainingDetailApiResponse> trainingDetailResponseDtoMapper,
            @Qualifier("bootcampApiExecuter") TrainingApiExecuter bootcampTrainingApiExecuter,
            @Qualifier("dualTrainingApiExecuter") TrainingApiExecuter dualTrainingApiExecuter,
            MemberRepository memberRepository,
            MemberTrainingScrapRepository memberTrainingScrapRepository
    ) {
        this.trainingDetailResponseDtoMapper = trainingDetailResponseDtoMapper;
        this.bootcampTrainingApiExecuter = bootcampTrainingApiExecuter;
        this.dualTrainingApiExecuter = dualTrainingApiExecuter;
        this.memberRepository = memberRepository;
        this.memberTrainingScrapRepository = memberTrainingScrapRepository;
    }

    public TrainingScrapResponseDto saveTraining(TrainingSaveReqeustDto request, TrainingType trainingType){
        Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
                .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if(memberTrainingScrapRepository.existsByTrainingIdAndMemberId(request.trprId(), member.getId())){
            throw TrainingErrorCode.IS_SAVED_TRAINING.toException();
        }

        TrainingDetailApiResponse response;
        if(trainingType.name().equals(TrainingType.BOOTCAMP.getDescription())){
            response = trainingDetailResponseDtoMapper.jsonToResponseDto(
                    bootcampTrainingApiExecuter.callDetailApi(
                            request.trprId(), request.trprDegr(), request.trainstCstId()
                    )
            );

        }else{
            response = trainingDetailResponseDtoMapper.jsonToResponseDto(
                    dualTrainingApiExecuter.callDetailApi(
                            request.trprId(), request.trprDegr(), request.trainstCstId()
                    )
            );

        }
        memberTrainingScrapRepository.save(create(trainingType, member, request, response));


        return new TrainingScrapResponseDto(
                member.getId(),
                true
        );
    }


    private MemberTrainingScrap create(
            TrainingType trainingType, Member member,
            TrainingSaveReqeustDto request, TrainingDetailApiResponse response
    ){
        return MemberTrainingScrap.of(
                trainingType, member,
                request, response
        );
    }
}
