package com.dodream.training.controller;

import com.dodream.training.application.BootcampService;
import com.dodream.training.application.DualTrainingService;
import com.dodream.training.controller.swagger.TrainingSwagger;
import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.core.presentation.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate
    ){
        if(type.equals("이론 위주")){
            return ResponseEntity.ok(new RestResponse<>(
                    bootcampService.getList(pageNum, regionName, jobName, startDate, endDate)
            ));
        }else{
            return ResponseEntity.ok(new RestResponse<>(
                    dualTrainingService.getList(pageNum, regionName, jobName, startDate, endDate))
            );
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<RestResponse<TrainingDetailApiResponse>> getBootcampDetail(
            @RequestParam(required = false) String type,
            @RequestParam String srchTrprId,
            @RequestParam String srchTrprDegr,
            @RequestParam String srchTorgId
    ){
        if(type.equals("이론 위주")){
            return ResponseEntity.ok(new RestResponse<>(
                    bootcampService.getDetail(srchTrprId, srchTrprDegr, srchTorgId)
            ));
        }else{
            return ResponseEntity.ok(new RestResponse<>(
                    dualTrainingService.getDetail(srchTrprId, srchTrprDegr, srchTorgId)
            ));
        }
    }
}
