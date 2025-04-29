package com.dodream.recruit.presentation;

import com.dodream.core.presentation.RestResponse;
import com.dodream.recruit.application.RecruitService;
import com.dodream.recruit.dto.response.RecruitResponseDetailDto;
import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/recruit")
@RequiredArgsConstructor
public class RecruitController implements RecruitSwagger{

    private final RecruitService recruitService;

    @Override
    @GetMapping("/list")
    public ResponseEntity<RestResponse<RecruitResponseListDto>> getRecruitListController(
            @RequestParam int pageNum,
            @RequestParam(required = false) String keyWord,
            @RequestParam(required = false) String locationCode
    ){
        return ResponseEntity.ok(new RestResponse<>(
                recruitService.getRecruitList(keyWord, locationCode, pageNum)
        ));
    }

    @Override
    @GetMapping("/detail")
    public ResponseEntity<RestResponse<RecruitResponseDetailDto>> getRecruitDetailController(
            @RequestParam String id
    ) {
        return ResponseEntity.ok(new RestResponse<>(recruitService.getRecruitDetail(id)));
    }
}
