package com.dodream.scrap.repository.training;

import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberTrainingScrapRepositoryCustom {
    Page<MemberTrainingScrap> searchWithFilter(Long memberId, String locName, String sortBy, int pageSize, int pageNum);

    List<String> findScrapedRecruitId(Long memberId, List<String> trainingIds);
}
