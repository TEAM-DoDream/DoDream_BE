package com.dodream.ncs.infrastructure;

import com.dodream.common.dto.response.CommonResponse;
import com.dodream.ncs.application.NcsApiService;
import com.dodream.ncs.domain.Ncs;
import com.dodream.ncs.repository.NcsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class NcsInitiallizer {

    private final NcsApiService ncsApiService;
    private final NcsRepository ncsRepository;

    @PostConstruct
    public void init() {
        if(ncsRepository.count() != 0) return;

        log.info("[NcsInitiallizer] - NCS 직무 코드 데이터 저장 시작");

        List<Ncs> largeNcsList = searchResultToNcsList(ncsApiService.getLargeNcsInfo());
        log.info("[NcsInitiallizer] - 대분류 - {}개 검색", largeNcsList.size());

        List<Ncs> middleNcsList = searchResultToNcsList(ncsApiService.getMiddleNcsInfo());
        log.info("[NcsInitiallizer] - 중분류 - {}개 검색", middleNcsList.size());

        List<Ncs> smallNcsList = searchResultToNcsList(ncsApiService.getSmallNcsInfo());
        log.info("[NcsInitiallizer] - 소분류 - {}개 검색", smallNcsList.size());

        List<Ncs> smallestNcsList = searchResultToNcsList(ncsApiService.getSmallestNcsInfo());
        log.info("[NcsInitiallizer] - 세분류 - {}개 검색", smallestNcsList.size());

        ncsRepository.saveAll(largeNcsList);
        ncsRepository.saveAll(middleNcsList);
        ncsRepository.saveAll(smallNcsList);
        ncsRepository.saveAll(smallestNcsList);

        log.info("[NcsInitiallizer] - NCS 직무 코드 데이터 저장 종료");
    }


    private List<Ncs> searchResultToNcsList(CommonResponse.SrchList srchList){
        return srchList.scnList()
                .stream()
                .map(item -> Ncs.of(
                        item.rsltCode(),
                        item.rsltName()
                ))
                .toList();
    }
}
