package com.dodream.scrap.repository.training;

import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import org.springframework.data.domain.Page;

public interface MemberTrainingScrapRepositoryCustom {
    Page<MemberTrainingScrap> searchWithFilter(Long memberId, String locName, String sortBy, int pageSize, int pageNum);
}
