package com.dodream.core.util.init;

import com.dodream.region.domain.Region;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Profile("local")
//@Component          // 프로필이 local 일 때만 활성화
@RequiredArgsConstructor
public class DummyDataInitializer {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;

    /** 한 번에 DB로 보내는 레코드 수 */
    private static final int BATCH_SIZE = 50;

    /** 총 회원 수, 회원 1명당 스크랩 수 */
    private static final int MEMBER_COUNT = 20000;
    private static final int SCRAP_PER_MEMBER = 50;

    /** 초기화 진입점 */
//    @PostConstruct
    @Transactional
    public void init() {
        Long regionId = fetchAnyRegionId();
//        insertMembers(regionId);
        insertMemberRecruitScraps();
        log.info("✅ 더미 데이터 삽입 완료");
    }

    private void insertMembers(Long regionId) {
        final String sql =
                "INSERT INTO member (" +
                        "  login_id, password, nick_name, birth_date, gender, " +
                        "  profile_image, region_id, state, deleted, created_at, updated_at" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object[]> batchArgs = new ArrayList<>(BATCH_SIZE);

        for (int i = 1; i <= MEMBER_COUNT; i++) {
            batchArgs.add(new Object[]{
                    "user" + i,
                    passwordEncoder.encode("password" + i),
                    "nickname" + i,
                    LocalDate.of(1990, 1, 1).plusDays(i),
                    (i % 2 == 0 ? "MALE" : "FEMALE"),
                    null,
                    regionId,
                    "ACTIVE",
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            });

            if (i % BATCH_SIZE == 0) {
                log.info("▶ MEMBER {}건 삽입 완료", i);
                jdbcTemplate.batchUpdate(sql, batchArgs);
                batchArgs.clear();
            }
        }
        if (!batchArgs.isEmpty()) {
            jdbcTemplate.batchUpdate(sql, batchArgs);
        }
        log.info("▶ MEMBER {}건 삽입 완료", MEMBER_COUNT);
    }

    private void insertMemberRecruitScraps() {
        final String selectSql = "SELECT id, login_id FROM member";
        List<MemberMeta> members = jdbcTemplate.query(
                selectSql,
                (rs, rowNum) -> new MemberMeta(rs.getLong("id"), rs.getString("login_id"))
        );

        List<String> regions = jdbcTemplate.query(
                "SELECT region_name FROM region",
                (rs, rowNum) -> rs.getString("region_name")
        );

        final String insertSql =
                "INSERT INTO member_recruit_scrap (" +
                        "  recruit_id, title, company_name, expiration_date, location_name, " +
                        "  job_type, experience_level, education_level, close_type, recruit_url, " +
                        "  member_id, deleted, created_at, updated_at" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object[]> batchArgs = new ArrayList<>(BATCH_SIZE);

        for (MemberMeta m : members) {
            for (int idx = 1; idx <= SCRAP_PER_MEMBER; idx++) {
                int randomNumber = (int) (Math.random() * regions.size());
                batchArgs.add(new Object[]{
                        "RecruitId-" + m.loginId + "-" + idx,
                        "더미 채용 정보 - " + idx,
                        "회사명" + idx,
                        "2025-01-01T00:00:00+0900",
                        regions.get(randomNumber),
                        "정규직",
                        "신입/경력",
                        "고등학교 졸업 이상",
                        "SPECIFIC_DATE",
                        "https://saramin/" + idx,
                        m.id,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                });

                if (batchArgs.size() >= BATCH_SIZE) {
                    jdbcTemplate.batchUpdate(insertSql, batchArgs);
                    batchArgs.clear();
                }
            }
        }
        if (!batchArgs.isEmpty()) {
            jdbcTemplate.batchUpdate(insertSql, batchArgs);
        }
        log.info("▶ MEMBER_RECRUIT_SCRAP {}건 삽입 완료", members.size() * SCRAP_PER_MEMBER);
    }

    /** region 테이블에서 아무 id 하나 가져오기 (로컬 DB에 최소 1건 존재한다고 가정) */
    private Long fetchAnyRegionId() {
        return jdbcTemplate.queryForObject("SELECT id FROM region LIMIT 1", Long.class);
    }

    /** 회원 PK·loginId 보관용 */
    private record MemberMeta(Long id, String loginId) {}
}
