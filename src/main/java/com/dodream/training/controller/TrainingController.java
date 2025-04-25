package com.dodream.training.controller;

import com.dodream.training.application.TrainingService;
import com.dodream.training.controller.swagger.TrainingSwagger;
import com.dodream.training.dto.response.BootcampDetailApiResponse;
import com.dodream.training.dto.response.BootcampListApiResponse;
import com.dodream.core.presentation.RestResponse;
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

    private final TrainingService trainingService;

    @GetMapping("/list")
    public ResponseEntity<RestResponse<BootcampListApiResponse>> getBootcampList(
        @RequestParam String pageNum,
        @RequestParam(required = false) String regionName,
        @RequestParam(required = false) String ncsName
    ){
        return ResponseEntity.ok(new RestResponse<>(
                trainingService.getList(pageNum, regionName, ncsName))
        );
    }

    @GetMapping("/detail")
    public ResponseEntity<RestResponse<BootcampDetailApiResponse>> getBootcampDetail(
            @RequestParam String srchTrprId,
            @RequestParam String srchTrprDegr,
            @RequestParam String srchTorgId
    ){
        return ResponseEntity.ok(new RestResponse<>(
                trainingService.getDetail(srchTrprId, srchTrprDegr, srchTorgId)
        ));
    }
}
