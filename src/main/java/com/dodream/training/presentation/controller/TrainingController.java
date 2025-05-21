package com.dodream.training.presentation.controller;

import com.dodream.training.application.BootcampService;
import com.dodream.training.application.DualTrainingService;
import com.dodream.training.presentation.swagger.TrainingSwagger;
import com.dodream.scrap.domain.value.TrainingType;
import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.core.presentation.RestResponse;
import com.dodream.training.presentation.value.SortBy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/training")
@RequiredArgsConstructor
public class TrainingController implements TrainingSwagger {

    private final BootcampService bootcampService;
    private final DualTrainingService dualTrainingService;

    @GetMapping("/list")
    public ResponseEntity<RestResponse<TrainingListApiResponse>> getBootcampList(
        @RequestParam String pageNum,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String regionName,
        @RequestParam(required = false) String jobName,
        @RequestParam(required = false) String sortBy
    ){

        SortBy sort = SortBy.DEADLINE_ASC;
        if (SortBy.DEADLINE_DESC.getName().equals(sortBy)) {
            sort = SortBy.DEADLINE_DESC;
        }

        TrainingListApiResponse response = TrainingType.DUAL.getDescription().equals(type)
                ? dualTrainingService.getList(pageNum, regionName, jobName, sort)
                : bootcampService.getList(pageNum, regionName, jobName, sort);

        return ResponseEntity.ok(new RestResponse<>(response));
    }

    @GetMapping("/detail")
    public ResponseEntity<RestResponse<TrainingDetailApiResponse>> getBootcampDetail(
            @RequestParam(required = false) String type,
            @RequestParam String srchTrprId,
            @RequestParam String srchTrprDegr,
            @RequestParam String srchTorgId
    ){
        if(TrainingType.DUAL.getDescription().equals(type)){
            return ResponseEntity.ok(new RestResponse<>(
                    dualTrainingService.getDetail(srchTrprId, srchTrprDegr, srchTorgId)
            ));
        }else{
            return ResponseEntity.ok(new RestResponse<>(
                    bootcampService.getDetail(srchTrprId, srchTrprDegr, srchTorgId)
            ));
        }
    }
}
