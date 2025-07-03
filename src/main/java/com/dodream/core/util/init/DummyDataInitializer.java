package com.dodream.core.util.init;

import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.repository.MemberRepository;
import com.dodream.region.domain.Region;
import com.dodream.region.repository.RegionRepository;
import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.dodream.scrap.domain.value.RecruitCloseType;
import com.dodream.scrap.repository.MemberRecruitScrapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
* 부하 테스트 및 성능 테스트를 위한 더미데이터 init 클래스
* 절대 prod 환경에서 실행 불가능하게 하기
* */

@Profile("local")
//@Component
@RequiredArgsConstructor
public class DummyDataInitializer {

    private final MemberRepository memberRepository;
    private final MemberRecruitScrapRepository memberRecruitScrapRepository;
    private final RegionRepository regionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    @PostConstruct
    @Transactional
    public void initUserData() {
        Region region = regionRepository.findAll().stream().findAny().get();

        for(int i = 1; i <= 1000; i++){
            Member member = memberRepository.save(
                    Member.builder()
                            .loginId("user" + i)
                            .password(bCryptPasswordEncoder.encode("password" + i))
                            .nickName("nickname" + i)
                            .birthDate(LocalDate.of(1990, 1, 1).plusDays(i))
                            .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                            .region(region)
                            .build()
            );

            List<MemberRecruitScrap> scraps = new ArrayList<>();

            for(int j = 1; j <= 50; j++){
                    scraps.add(
                        MemberRecruitScrap.builder()
                            .recruitId("RecruitId-" + i + "-" + j)
                            .title("더미 채용 정보 - " + j)
                            .companyName("회사명" + j)
                            .expirationDate("2025-01-01T00:00:00+0900")
                            .locationName("서울 종로구")
                            .jobType("정규직")
                            .experienceLevel("신입/경력")
                            .educationLevel("고등학교 졸업 이상")
                            .closeType(RecruitCloseType.SPECIFIC_DATE)
                            .recruitUrl("https://saramin/" + j)
                            .member(member)
                            .build()
                );
            }

            // Batch-insert => yaml 설정 필요
            memberRecruitScrapRepository.saveAll(scraps);

            if (i % 100 == 0) {
                memberRepository.flush();
            }
        }
    }
}
