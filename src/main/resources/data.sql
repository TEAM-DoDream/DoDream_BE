-- table 만들기
drop table certification;
drop table job;

-- 직업
CREATE TABLE IF NOT EXISTS job (
     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     created_at DATETIME NOT NULL,
     updated_at DATETIME NOT NULL,
     deleted BOOLEAN NOT NULL,

     job_name VARCHAR(255) NOT NULL,
     requires_certification ENUM('REQUIRED', 'OPTIONAL', 'NONE') NOT NULL,
     work_time_slot ENUM('WEEKDAY_MORNING', 'WEEKDAY_AFTERNOON', 'WEEKDAY_NINE_TO_SIX', 'WEEKEND', 'EVENT', 'FLEXIBLE') NOT NULL,
     salary_type ENUM('MONTHLY', 'DAILY', 'PER_CASE') NOT NULL,
     salary_cost INT NOT NULL,

     interpersonal_contact_level ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL,
     physical_activity_level ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL,
     emotional_labor_level ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL,

     job_image_url VARCHAR(1000),
     ncs_name VARCHAR(255) NOT NULL,
     job_code VARCHAR(50) NOT NULL
);


-- 자격증
CREATE TABLE IF NOT EXISTS certification (
   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   created_at DATETIME NOT NULL,
   updated_at DATETIME NOT NULL,
   deleted BOOLEAN NOT NULL,

   certification_name VARCHAR(255) NOT NULL,
   certification_preparation_period VARCHAR(255) NOT NULL,

   job_id BIGINT,
   CONSTRAINT fk_certification_job FOREIGN KEY (job_id) REFERENCES job(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS job_todo (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted BOOLEAN NOT NULL,

    title VARCHAR(255) NOT NULL,
    todo_category VARCHAR(255) NOT NULL,
    job_id BIGINT NOT NULL,

    CONSTRAINT fk_job_todo_job_id FOREIGN KEY (job_id)
        REFERENCES job(id)

);


-- 직업 목록 init
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'HIGH', 'HIGH', 'K000000850', '', '요양보호사', '요양지원', 'HIGH', 'REQUIRED', '220', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP,'HIGH', 'HIGH', 'K000007576', '', '간호조무사', '감염관리', 'MEDIUM', 'REQUIRED', '230', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'HIGH', 'HIGH', 'K000007502', '', '보육교사', '아이돌봄', 'HIGH', 'REQUIRED', '220', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'HIGH', 'HIGH', 'K000000857', '', '사회복지사', '사회복지면담', 'MEDIUM', 'REQUIRED', '250', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'MEDIUM', 'HIGH', 'K000007565', '', '직업상담사', '직업상담', 'LOW', 'REQUIRED', '250', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'HIGH', 'HIGH', 'K000001049', '', '심리상담사', '심리상담', 'LOW', 'REQUIRED', '250', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'MEDIUM', 'MEDIUM', 'K000007556', '', '급식 도우미', '음식조리', 'HIGH', 'NONE', '170', 'MONTHLY', 'WEEKDAY_MORNING');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'LOW', 'LOW', 'K000001219', '', '사무보조원', '사무행정', 'LOW', 'OPTIONAL', '210', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'LOW', 'LOW', 'K000007487', '', '회계사무원', '회계·감사', 'LOW', 'OPTIONAL', '240', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'LOW', 'LOW', 'K000007497', '', '수의테크니션', '수의서비스', 'LOW', 'REQUIRED', '220', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'HIGH', 'HIGH', 'K000001167', '', '웨딩 헬퍼', '결혼서비스', 'HIGH', 'NONE', '20', 'DAILY', 'EVENT');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'MEDIUM', 'HIGH', 'K000007481', '', '미용사 (일반)', '헤어미용', 'HIGH', 'REQUIRED', '250', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'HIGH', 'HIGH', 'K000001091', '', '미용사 (피부)', '피부미용', 'HIGH', 'REQUIRED', '250', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'HIGH', 'HIGH', 'K000000972', '', '미용사 (네일)', '메이크업', 'MEDIUM', 'REQUIRED', '230', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'HIGH', 'HIGH', 'K000001178', '', '미용사 (메이크업)', '네일미용', 'MEDIUM', 'REQUIRED', '250', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'LOW', 'MEDIUM', 'K000007477', '', '반려동물미용사', '애완동물미용', 'HIGH', 'REQUIRED', '250', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'MEDIUM', 'HIGH', 'K000000981', '', '레크리에이션 지도사', '레크리에이션지도', 'HIGH', 'OPTIONAL', '10', 'PER_CASE', 'EVENT');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'LOW', 'MEDIUM', 'K000000893', '', '바리스타', '커피관리', 'MEDIUM', 'OPTIONAL', '190', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'HIGH', 'HIGH', 'K000007536', '', '공인중개사', '부동산', 'MEDIUM', 'REQUIRED', '250', 'MONTHLY', 'FLEXIBLE');
INSERT INTO job (created_at, deleted, updated_at, emotional_labor_level, interpersonal_contact_level, job_code, job_image_url, job_name, ncs_name, physical_activity_level, requires_certification, salary_cost, salary_type, work_time_slot) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 'HIGH', 'HIGH', 'K000001146', '', '산후조리사', '산후육아지원', 'MEDIUM', 'OPTIONAL', '230', 'MONTHLY', 'WEEKDAY_NINE_TO_SIX');

-- 자격증
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '1', '요양보호사 자격증', '1개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '2', '간호조무사 자격증', '1년');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '3', '보육교사 3급', '6~12개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '4', '사회복지사 2급', '1~2년');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '5', '직업 상담사 2급', '6~12개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '6', '임상심리사 2급', '3~6개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '8', 'ITQ정보기술자격증', '1~2개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '8', '컴퓨터활용능력2급', '1~2개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '9', '전산 회계 2급', '1~2개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '10', '동물 보건사 자격증', '1개월 내');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '12', '미용사(일반) 자격증', '3~6개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '13', '피부관리사 자격증', '3~6개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '13', '미용사(피부) 자격증', '3~6개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '14', '미용사 (네일) 자격증', '4~5개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '15', '미용사 (메이크업)자격증', '3~6개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '16', '애견미용사자격증', '3~6개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '16', '반려견 스타일리스트 자격증', '3~6개월');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '17', '레크리에이션 지도사 자격증', '1개월 내');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '18', '바리스타 자격증', '1개월 내');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '19', '공인중개사', '6개월~1년');
INSERT INTO certification (created_at, deleted, updated_at, job_id, certification_name, certification_preparation_period) VALUES (CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, '20', '산후조리사 자격증(민간)', '1~2개월');

-- 직업별 투두
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “요양보호사 하루일과” 영상 시청 후 실제 하루 스케줄, 업무 강도 중심으로 메모하기','PREPARE', '1');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '‘한국보건의료인국가시험원’ 홈페이지에서 방문하여 ‘자격 요건’, ‘시험 일정’, ‘시험 과목’ 확인 후 메모하기', 'PREPARE', '1');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '네이버 또는 구글에서 “[지역명] 요양보호사 교육기관” 검색하여 집 근처 수강 가능 기관 찾아보기', 'PREPARE', '1');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 고용24에 요양보호사 자격 교육 수강 신청하기', 'START', '1');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '요양보호사 자격시험 대비 문제집 구매 후 계획 수립하기 (예: 주 3회, 하루 30분~1시간 학습 계획 세우기)', 'START', '1');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '관심 있는 요양기관에 직접 전화/방문하여 업무 환경 및 초보 수습 기간 여부 문의해보기', 'START', '1');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '시험일정에 맞춰 요양보호사 국가자격시험 응시표 출력 & 수험표 준비하기', 'CHALLENGE', '1');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림, 사람인, 고용24 등에서 “요양보호사 구인” 검색하여 채용 공고 확인하기', 'CHALLENGE', '1');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '첫 출근 전, 유튜브에서 “초보 요양보호사 팁” 검색해 돌봄 노하우 미리 숙지하기', 'CHALLENGE', '1');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “병원 간호조무사 일상” 검색해 외래/입원 병동, 한의원 등 병원별 근무 형태 비교하기', 'PREPARE', '2');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '‘간호조무사협회’ 또는 ‘보건복지부 간호조무사 교육기관 검색’ 사이트에서 자격 요건과 과정 확인하기', 'PREPARE', '2');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '구글 또는 네이버에 “[지역명] 간호조무사 학원” 검색하여 근처 교육기관 찾고 메모하기 (추천 기준: 1년 이상 과정 개설 여부, 국가고시 합격률, 실습처 연계 상황 비교)', 'PREPARE', '2');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 ‘고용24’에서 가장 빠른 개강일 기준으로 간호조무사 학원 등록하기: 실습기관 연계 여부, 국가시험 대비반 운영 여부 메모해두기', 'START', '2');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '실습 병원 배정받고 출석률/수업 진도 체크하며 수강 시작하기', 'START', '2');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '간호조무사 자격시험 대비 교재 구매 후 계획 수립하기 (예: 주 3회, 하루 30분~1시간 학습 계획 세우기)', 'START', '2');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '보건복지부 간호조무사 국가시험 일정 확인 후 응시표 출력 & 수험표 준비하기', 'CHALLENGE', '2');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '이력서 및 면접 준비하기: 성실함, 장기근무 의지, 간호 조무 실습 경험 강조하여 구성하기', 'CHALLENGE', '2');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림, 메디플, 병원잡 등에서 “간호조무사” 검색하여 병원/한의원 채용 공고 지원하기: 내과/한의원/요양병원 등 병원유형과 급여 비교 메모하기', 'CHALLENGE', '2');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에 “보육교사 일상” 검색하여 아이 연령대별 분위기 차이, 교사 간 협업 구조, 학부모 대응 난이도 메모하기', 'PREPARE', '3');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '‘보육인력국가자격증’ 사이트에서 보육교사 2급 자격 요건 확인하기', 'PREPARE', '3');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '구글 또는 네이버에 “[지역명] 보육교사 학점은행제,” “온라인 평생교육원 보육교사” 검색하여 교육기관 리스트 확인하기 (주의: 평가 인정 받은 교육기관 여부 체크)', 'PREPARE', '3');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '학점은행제 또는 평생교육원 통해 보육교사 관련 필수/선택 과목 신청하기', 'START', '3');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '보육교사 필수(8과목) + 선택(6과목 이상) 총 12과목 수강 계획 세우기 (수강 추천 순서: ‘보육학개론 → 아동발달 → 보육과정 → 아동권리와 복지’)', 'START', '3');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '이론 수강과 병행해 보육실습 가능한 어린이집 섭외 및 실습 일정 조율하기', 'START', '3');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '교육 이수 후 ‘보육인력관리센터’에 자격증 발급 신청하기', 'CHALLENGE', '3');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림, 사람인, 알바몬 등에서 “어린이집 보조교사,” “보육교사 보조” 공고 검색 및 지원하기 (주의: 아동 연령대, 정원 수, 교사 수 대비 아동 비율, 야근·당직 여부 확인)', 'CHALLENGE', '3');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '첫 출근 전, “아동 행동 대처법,” “교실 운영 노하우” 등 키워드로 유튜브 자료 찾아보며 실무 감각 익히기', 'CHALLENGE', '3');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브 또는 네이버에 “사회복지사 하는 일”, “사회복지사 브이로그” 검색하여 노인복지관, 지역아동센터, 정신건강센터 등 유형별 분위기 메모하기', 'PREPARE', '4');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '국가평생교육진흥원 또는 해커스 원격 평생 교육원에서 “사회복지사 2급 자격 취득 방법”, “학점 기준(총 17과목)”, “실습 조건” 확인하기', 'PREPARE', '4');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '사회복지사 2급 과목(총 17과목) 이수 가능한 교육기관 수강신청하기 (예: 해커스, 서울디지털대학교 등)', 'START', '4');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '지역 내 실습 기관 리스트 확인 후, 실습 신청 및 일정 조율하기 (예: 노인복지관, 사회복지관)', 'START', '4');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '온라인 수업 수강하며, 주요 과목별 요점 정리 노트 작성하기', 'START', '4');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '사회복지현장실습 160시간 이상 이수 완료하고 실습 일지 작성 마무리하기', 'CHALLENGE', '4');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '자격증 발급 신청: 학점 취득 후 국가평생교육진흥원 등록 → 한국사회복지사협회 자격증 신청', 'CHALLENGE', '4');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두림, 복지넷 등에서 “사회복지사 구인” 검색해 지원 시작하기 (예: 노인복지관, 지역아동센터 등)', 'CHALLENGE', '4');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “직업상담사 하는 일” 검색하여 민간·공공 상담 업무의 차이, 고객군(청년, 경력단절, 중장년) 별 응대 방식 메모하기', 'PREPARE', '5');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '큐넷(Q-Net)에서 ‘직업상담사 2급’ 자격 요건 및 시험 일정 확인하기', 'PREPARE', '5');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 네이버에서 “[지역명] 직업상담사 학원”, “직업상담사 인강” 검색하여 교육기관 및 교재 정보 확인하기', 'PREPARE', '5');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '필기 과목 직업상담학, 직업심리학, 직업정보론, 노동시장론, 노동관계법규: 수강 계획 세우기 (예: 하루 12시간 집중 학습 계획 수립)', 'START', '5');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '필기 과목 ‘큐넷 기출문제 PDF’에서 기출문제 3개년치 다운로드하여 문제 유형 파악하고 반복 풀이하기', 'START', '5');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '실기 과목 시험 준비를 위해 “상담 시나리오”, “직업 상담 사례” 영상 및 자료 정리 (주요 유형: 구직자 상담, 직업정보 제공, 경력단절자 재취업 상담 등)', 'START', '5');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '필기 및 실기 시험 일정 맞춰 응시표 출력 & 수험표 준비하기', 'CHALLENGE', '5');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '고용센터, 여성인력개발센터, 청소년진로센터 등 관련 기관에 이력서 제출하기 (채용유형 확인: 정규직 / 공무직 / 위촉직 / 시간제 상담사)', 'CHALLENGE', '5');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '첫 출근 전, 실제 구직자에게 자주 묻는 질문과 직업정보 제공법 정리해보기 (예: 워크넷 활용법, 자격증 추천 키워드 정리 등)', 'CHALLENGE', '5');


INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브나 네이버에 “심리상담사 하는 일” 검색하여 공공기관/학교/민간센터 등 근무 환경 차이 메모하기', 'PREPARE', '6');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '“심리상담사”, “청소년 상담사”, “상담심리사” 차이점 정리 후 나의 상황에 맞는 자격 루트 결정하기 (예: 청소년상담사 3급, 상담심리사 2급, 민간 심리상담사 등)', 'PREPARE', '6');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '“심리상담사 자격증 과정 온라인” 검색하여 민간자격/국가자격 교육기관 살펴보기 (예: 한국상담심리학회, 지역 평생교육원 등)', 'PREPARE', '6');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 사람인에서 자격 요건에 맞는 상담 관련 교육기관 수강 신청하기 (예: 상담심리사 2급, 청소년상담사 3급 등)', 'START', '6');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '필수 이론 과목(상담이론, 이상심리학, 발달심리학, 성격심리학) 수강하며, 강의 주요 개념 요약 정리하기', 'START', '6');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '전화상담센터, 청소년상담센터, 정신건강복지관 등 실습 기관 탐색 및 일정 조율하기', 'START', '6');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '시험 일정에 맞추어 응시표 출력 & 수험표 준비하기', 'CHALLENGE', '6');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '자격 취득 후, 사람인/워크넷 등에서 “심리상담사 채용”, “청소년상담사 채용” 키워드로 공고 검색해보기', 'CHALLENGE', '6');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '전화 상담, 비대면 상담 등 다양한 상담 형태에 익숙해지기 위해 관련 사례나 모의상담 영상 시청하기', 'CHALLENGE', '6');



