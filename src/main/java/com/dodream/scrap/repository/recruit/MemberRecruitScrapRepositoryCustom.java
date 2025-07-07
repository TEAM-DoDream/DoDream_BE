package com.dodream.scrap.repository.recruit;

import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberRecruitScrapRepositoryCustom {
    Page<MemberRecruitScrap> searchWithFilter(Long memberId, String locName, String sortBy, int pageSize, int pageNum);

    List<String> findScrapedRecruitId(Long memberId, List<String> recruitId);
}
