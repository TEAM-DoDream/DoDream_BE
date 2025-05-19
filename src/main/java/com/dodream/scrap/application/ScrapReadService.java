package com.dodream.scrap.application;

import com.dodream.scrap.repository.MemberRecruitScrapRepository;
import com.dodream.scrap.repository.MemberTrainingScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapReadService {

    private final MemberRecruitScrapRepository memberRecruitScrapRepository;
    private final MemberTrainingScrapRepository memberTrainingScrapRepository;

}
