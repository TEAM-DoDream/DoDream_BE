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

    CONSTRAINT fk_job_todo_job_id FOREIGN KEY (job_id) REFERENCES job(id) ON DELETE CASCADE

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

-- 직업 요약 추가 방법 (추후 직업 INSERT문에 합칠 예정)
alter table job drop column job_code;

UPDATE job SET job_summary = '어르신의 일상생활을 도와드리는 따뜻한 돌봄 직업이에요.' where id=1;
UPDATE job SET job_summary = '병원이나 의원에서 진료를 보조하고 환자를 돌보는 일을 해요.' where id=2;
UPDATE job SET job_summary = '아이들을 돌보며 건강하고 안전한 하루를 만들어주는 직업이에요.' where id=3;
UPDATE job SET job_summary = '도움이 필요한 이웃에게 복지 서비스를 연결해주는 따뜻한 일이에요.' where id=4;
UPDATE job SET job_summary = '구직자에게 맞는 일자리를 찾도록 돕고 이력서·면접 준비도 도와주는 역할을 해요.' where id=5;
UPDATE job SET job_summary = '마음을 힘들어하는 사람들의 이야기를 듣고 위로를 전하는 일이에요.' where id=6;
UPDATE job SET job_summary = '학교나 복지시설 등에서 식재료 준비, 배식, 정리까지 함께 돕는 일이에요.' where id=7;
UPDATE job SET job_summary = '서류 정리, 전화 응대 등 사무실의 다양한 업무를 담당해요.' where id=8;
UPDATE job SET job_summary = '영수증 정리, 비용 입력 등 돈과 관련된 회사 기록을 꼼꼼하게 정리하는 일을 해요.' where id=9;
UPDATE job SET job_summary = '동물 병원에서 수의사를 도와 동물을 치료하고 돌보는 직업이에요.' where id=10;
UPDATE job SET job_summary = '결혼식 당일, 신부님 옷과 액세서리, 이동 등을 도와주는 일이에요.' where id=11;
UPDATE job SET job_summary = '고객의 머리 손질을 돕고 미용 서비스를 제공하는 일이에요.' where id=12;
UPDATE job SET job_summary = '피부 관리실에서 얼굴, 피부 마사지 등 미용 관리를 해주는 일이에요.' where id=13;
UPDATE job SET job_summary = '손톱·발톱 관리와 네일아트를 통해 고객의 손을 예쁘게 꾸며주는 일이에요.' where id=14;
UPDATE job SET job_summary = '결혼식, 행사, 촬영 등을 위해 사람의 얼굴 화장을 전문적으로 해주는 직업이에요.' where id=15;
UPDATE job SET job_summary = '반려동물의 털을 깔끔하게 다듬고 목욕시키는 일을 해요.' where id=16;
UPDATE job SET job_summary = '어르신이나 아이들 대상 프로그램을 진행하며 놀이, 게임 등 활동을 안내해요. ' where id=17;
UPDATE job SET job_summary = '커피를 만들고 손님과 소통하는 따뜻한 공간을 만드는 일이에요.' where id=18;
UPDATE job SET job_summary = '사무실에서 부동산 매매나 임대 계약을 돕는 업무를 해요.' where id=19;
UPDATE job SET job_summary = '출산 후 산모와 아기를 돌보며 회복을 돕는 따뜻한 돌봄이에요.' where id=20;

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

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '네이버 또는 유튜브에 “급식 도우미 일상”, “급식 도우미 후기” 검색하여 배식 환경, 일과 시간 흐름 등 메모하기', 'PREPARE', '7');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '“급식 도우미 채용 사이트” 검색해서 알바몬, 잡코리아, 사람인 등 주요 구직 플랫폼 즐겨찾기 하기', 'PREPARE', '7');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '“급식 도우미 근무 조건” 키워드로 업무 시간대, 복장, 식단 보조 등 기본 정보 확인하기', 'PREPARE', '7');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '푸앤잡플러스, 구직 플랫폼(알바몬/워크넷 등) 회원가입 및 이력서 등록하기', 'START', '7');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '내 체력에 맞는 희망 근무 요일·시간대 체크하며, 구인 공고 확인하기(예: 오전 10시~오후 2시 근무)', 'START', '7');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '식품의약품안전처 및 각 지자체 보건소에서 지정한 기관에서 급식 위생 교육 수료 여부 확인하기', 'START', '7');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '지역 교육청/복지관에서 “급식도우미 위생 교육” 으로 검색하여 온라인 수강 계획 세우기', 'START', '7');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림, 푸드앤잡플로서, 지역 새일센터 등에서 “급식 도우미”, “학교 급식 보조” 채용 공고에 지원하기', 'CHALLENGE', '7');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '면접 전 “급식 보조 예절”, “학교 보조직 근무 요령” 검색해 기본 매너와 주의사항 파악하기', 'CHALLENGE', '7');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '첫 출근 전, 위생 관련 규칙(앞치마, 위생모 착용 등) 숙지하고, 근무 시 필요한 편한 신발·복장 준비하기', 'CHALLENGE', '7');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에 “사무직/행정직 일과” 검색하여 실제 사무 환경과 업무 범위(문서정리/출장비처리/회계보조 등) 메모하기', 'PREPARE', '8');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 사람인에서 “사무보조”, “행정사무원” 키워드로 공고 검색하기', 'PREPARE', '8');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '요구 및 우대 조건에서 ‘전산회계’, ‘컴활2급’ 등 관련 자격증 정보를 확인하고 필요 여부 검토하기', 'PREPARE', '8');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '문서 작성 프로그램(한글, 엑셀, 워드 등) 기본 기능 연습하기', 'START', '8');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '오프라인 교육기관이나 유튜브 강의로 컴퓨터 기초 사용법 복습하기', 'START', '8');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '근무 환경에 맞는 단정한 복장 준비하고, 사무실 내 기본 예절 확인하기', 'START', '8');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '원하는 조건(근무 요일, 시간대, 거리 등)에 맞는 사무직 공고에 지원하기', 'CHALLENGE', '8');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '사무보조 인턴, 단기 아르바이트 등으로 실무 감각 익히기', 'CHALLENGE', '8');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '첫 출근 전, “기본 전화 응대법”, “사무용어 정리” 등을 검색하여 업무 적응 준비하기', 'CHALLENGE', '8');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false,'유튜브·블로그에서 ‘회계사무원 일상’ 키워드로 검색 후 실제 업무 환경과 전표 입력, 세금계산서, 급여정리 등 메모하기', 'PREPARE', '9');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '‘더존’, ‘케이랩(K-Lep)’ 같은 회계 프로그램의 무료 사용법 영상 찾아보기', 'PREPARE', '9');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false,'워드, 엑셀 기초 활용법을 네이버 블로그나 유튜브에서 학습 시작하기 (추천 키워드: 회계 실무 엑셀 단축키, 회계용 엑셀 자동화)', 'PREPARE', '9');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '[전산회계 2급(한국세무사회)] 또는 [FAT 자격증(한국공인회계사회)] 중 하나 자격 요건 확인하고 준비하기', 'START', '9');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 사람인에 ‘회계보조’, ‘경리’ 키워드로 공고 비교 검색해보기', 'START', '9');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '지역 새일센터/여성인력개발센터에 ‘경리사무원 실무 과정’ 여부 문의하기','START', '9');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '가까운 학원 또는 온라인 과정으로 회계 자격증 교육 신청하기', 'CHALLENGE', '9');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '연습용 회계 프로그램 설치 후, 블로그 강의 따라가며 데이터 입력 실습하기', 'CHALLENGE', '9');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '일단 단기 근무 공고(회계보조, 단순 전표 입력 등)에 지원해 실전 감각 익히기', 'CHALLENGE', '9');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 ‘동물병원 보조 일상’, ‘수의테크니션’ 관련 영상 시청 후 동물 위생관리, 보조 업무 난이도 등 메모하기', 'PREPARE', '10');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '동물보건사 자격증 공식 요건 확인 (학력/인정 교육기관)', 'PREPARE', '10');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 고용24에서 “수의 테크니션” 관련 교육과정 검색 후 내일배움카드 지원 여부 확인하기', 'START', '10');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '‘동물병원 보조’ 아르바이트 공고 3곳 찾아 지원하기 (알바천국/벳잡스/도루스/사람인)', 'START', '10');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '실습 병원 유무 및 수업 커리큘럼 확인 후 교육기관 등록하기', 'START', '10');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '벳잡스·한국동물병원협회 채용 게시판에 이력서 등록 후, 주 1회 이상 지원하기', 'CHALLENGE', '10');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '관심 병원 3곳 이상 직접 방문 또는 전화 문의하기', 'CHALLENGE', '10');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '동물병원에서 사용하는 진료 보조 도구/기기등 기본 용어 목록화하여 학습하기', 'PREPARE', '11');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브의 “웨딩헬퍼 일상”, “결혼식 도우미 브이로그” 영상 시청 후 공통된 주요 역할 메모해보기', 'PREPARE', '11');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '실제 채용 공고 5개 스크랩하여 공통 요구사항 정리해보기 (복장, 체력, 나이 등)', 'PREPARE', '11');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '집 근처 예식장 5곳 전화하여 채용 가능성 문의해보기 → 실제 가능성 있는 곳은 메모해두기', 'PREPARE', '11');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '공고 조건 비교 후 나에게 맞는 일정 및 근무조건 정리한 개인 조건표 만들어보기', 'START', '11');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '인근 여성인력개발센터, 평생교육원, 고용24 등에서 진행하는 예절교육 또는 웨딩 실무 강의 알아보기', 'START', '11');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '“본식 순서”를 A4 한 장에 정리하고, 각 순서에서 웨딩헬퍼 역할 예측해보기', 'START', '11');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '체력 & 센스를 강조한 3문장 자기소개 작성해보고 말하기 연습하기', 'CHALLENGE', '11');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '‘위기 상황 대응 표’ 만들어보기 (예: 신랑 넥타이 분실, 신부 입장 시간 지연 시 대처법 등)', 'CHALLENGE', '11');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '실제 웨딩홀 아르바이트 시작 전, 첫 출근 준비물(단화, 검정 바지, 편한 상의 등) 미리 챙기기', 'CHALLENGE', '11');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브·블로그에서 ‘헤어 디자이너 브이로그’, “미용실 인턴 일상” 검색하여 실제 업무 목록 메모해보기', 'PREPARE', '12');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '네이버에서 “미용사(일반) 자격증 과목”, “헤어 국가자격증 실기” 검색하여 메모해보고 과목별 난이도 & 중요도 체크하기', 'PREPARE', '12');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림에서 “[지역명] 미용학원”, “헤어 국가자격증 학원” 3곳 검색한뒤 인근 학원 3곳 비교표 만들기 (위치/비용/수강 기간/재료비 포함 여부 등)', 'PREPARE', '12');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 고용24에서 “미용사 일반” 과정 검색 후 국비지원 과정 신청하기',  'START', '12');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '실기 도구 구매하기: 각 도구 사용법 1줄 요약 메모해보며 예습하기',  'START', '12');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '기본 이론서 구매하고 기본 미용 동영상 시청하며 커트, 펌, 드라이 기본 동작 예습하기',  'START', '12');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '큐넷(Q-net)에서 미용사(일반) 국가자격시험 일정 확인 후 이론+실기 시험 접수하기', 'CHALLENGE', '12');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림, 헤어인잡 등에서 “헤어 디자이너”, “미용사 인턴”, “미용사 신입” 키워드로 일자리 탐색하기', 'CHALLENGE', '12');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '면접 전, “내가 잘 할 수 있는 3가지”와 “배우고 싶은 점 3가지” 메모해보기', 'CHALLENGE', '12');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “피부미용사 브이로그”, “피부 관리사 하루일과” 검색해 실무 환경과 직무 흐름 이해하기', 'PREPARE', '13');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '네이버에서 “미용사(피부) 자격증 ” 검색해 시험 과목과 준비물 파악하기', 'PREPARE', '13');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '구글 또는 네이버에서 “[지역명] 피부미용 학원”, “국가자격 피부미용 교육기관” 검색해 학원 조사하기', 'PREPARE', '13');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, 'HRD-Net 또는 고용24에서 “미용사(피부)” 국비지원 과정 검색하고 내일배움카드 지원 가능 여부 확인하기', 'START', '13');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '학원에 문의해 실기 커리큘럼과 재료 포함 여부 확인 후 상담 예약하기', 'START', '13');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '실기 대비 도구(마사지크림, 타올, 스파츌라 등) 및 이론 교재 준비해 피부 구조와 시술 단계별 흐름 예습하기', 'START', '13');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '큐넷(Q-net)에서 미용사(피부) 국가자격시험 일정 확인하고 이론 및 실기 시험 접수하기', 'CHALLENGE', '13');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 사람인에서 “피부관리사”, “에스테틱 직원”, “피부미용사” 키워드로 구직 활동하기', 'CHALLENGE', '13');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “네일 아티스트 브이로그” 영상 시청 후 업무 내용 메모하기', 'PREPARE', '14');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '지역 네일샵 3곳 방문하고 직접 네일아트를 받으며 시술자에게 취준 고민, 현실적 조언 이야기 해보기', 'PREPARE', '14');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 고용24에서 “네일아트 자격증 과정”, “미용사(네일)” 국비 과정 검색 후 상담 신청 하기: 실습 키트 포함 여부(팁박스, 푸셔, 파일 등), 실습 횟수, 포트폴리오 수업 포함 여부 등', 'START', '14');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '가족, 친구, 지인에게 실기 연습 해보며 실전감각 익히기: 고객 입장에서의 조언 요청해보고 보완할 점 메모해두기', 'START', '14');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '큐넷(Q-net)에서 미용사(네일) 국가자격시험 일정 확인하고 이론+실기 접수하기', 'CHALLENGE', '14');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '아트 감각 익히기: SNS에서 ‘이달의 네일’ 검색 후 계정 3개 팔로우하기, 주마다 1개씩 셀프 디자인 제작한 뒤 포트폴리오 정리하기', 'CHALLENGE', '14');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 헤어인잡 등에서 “네일 아티스트”, “네일샵 직원”, “네일리스트” 키워드로 취업처 탐색하기', 'CHALLENGE', '14');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “중년 메이크업 아티스트 일상”, “시니어 메이크업” 영상 시청: 고객군, 메이크업 분위기, 업무 흐름 등 메모해두기', 'PREPARE', '15');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '내 관심 분야 정하기: 웨딩샵 / 백화점 브랜드 / 실버 모델 / 출장 / 무용인 전담 메이크업 등', 'PREPARE', '15');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '가족이나 친구에게 메이크업 해준 뒤 조언 받아보기', 'PREPARE', '15');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 고용24에서 “미용사 (메이크업)” 과정 검색 후 국비지원 유무 및 내일배움카드 적용 여부 확인하기', 'START', '15');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '희망 학원에 전화하여 상담 받아보기: 수업 일정, 실기 시험 도구 제공 여부, 모델 연습 기회, 포트폴리오 수업 포함 여부 물어보기', 'START', '15');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브, SNS의 아티스트 구독한 뒤 트렌드 감각 익히기: 40대/50대 메이크업, 아이브로우 그리기, 눈매 교정 메이크업 등 시청하며 관찰력 키우고 팁 메모해두기', 'START', '15');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '큐넷(Q-net)에서 미용사(메이크업) 국가자격시험 일정 확인 후 이론+실기 접수하기', 'CHALLENGE', '15');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '가족, 친구, 지인 대상으로 메이크업 진행하며 포트폴리오 제작하기: 모델 3인 이상 전/후 사진, 사용한 색조와 도구 구성, 집중한 부분, 고객 후기 정리', 'CHALLENGE', '15');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림, 사람인 등 플랫폼 통해 “메이크업 아티스트”, “웨딩샵 스텝”, “백화점 화장품 브랜드 직원”, “여성인력개발센터 실버 메이크업 강사” 구인공고 탐색하기', 'CHALLENGE', '15');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “반려동물 미용사 하루일과” 영상 3편 이상 시청한 뒤, 미용보다 힘든 점들 메모해보기', 'PREPARE', '16');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '내가 사는 지역의 반려동물 미용샵 3곳 검색/전화하기: 가격표, 운영 시간, 예약 방식, 공동된 운영방식, 브랜드 분위기 등 파악후 정리해보기', 'PREPARE', '16');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '자격증 종류 조사하기: 한국애견협회 vs 애견연맹 vs 민간기관 차이 비교하며 적합한 자격증 정하기', 'PREPARE', '16');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 고용24에서 “반려동물 미용사” 국비지원 과정 검색 후 신청하기', 'START', '16');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '미용 학원 상담 진행하며 내가 등록할 곳 비교분석하기: 실견 실습 수업 포함 여부, 모델견 실습 가능 횟수, 실습도구 제공 여부, 시험 응시 연계 여부 등', 'START', '16');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '주 2회 이상 모형 또는 지인 반려견과 자체 미용 실습 해본 뒤 사진으로 기록하고 개선점 메모하기', 'START', '16');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '시험 주관기관(예: 한국애견연맹, 한국애견협회)에서 시험 일정 확인하고 응시하기', 'CHALLENGE', '16');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 사람인에서 “반려동물 미용사” 키워드로 일자리 검색한 뒤 보조 아르바이트 부터 지원해보기', 'CHALLENGE', '16');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '첫 출근 전 유튜브에서 “초보 애견미용사 실수”, “견종별 미용 팁” 영상 검색해 실제 미용 현장 상황 대비하기', 'CHALLENGE', '16');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “레크레이션 강사 브이로그”, “레크레이션 진행 영상” 검색하여 실제 활동 방식과 분위기 익히기', 'PREPARE', '17');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '네이버에서 “레크레이션 지도사 자격증 종류”, “레크레이션 민간자격 유효성” 검색해 자격증 인정 여부 및 기관 비교하기', 'PREPARE', '17');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '나와 잘 맞을 고객층 설정하기: 아동/청소년, 노인/실버, 직장인 워크숍 등', 'PREPARE', '17');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 고용24에서 민간자격증 수강 신청하기: 실습 시연 기획 여부, 포트폴리오 제작 여부 확인하기', 'START', '17');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '현장 실습(기관 방문/모의 시연)할 때마다 내 활동 촬영하고, 대상 연령/반응 좋았던 활동/예상의 변수/개선점 메모하기', 'START', '17');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '자격증 공부하며 나만의 진행 스크립트 1안 만들어보기 (예: 오프닝 멘트 -> 게임 구성 -> 율동 -> 마무리 멘트 등)', 'START', '17');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '주관기관(예: 한국진로직업교육개발원, 한국여가레크리에이션협회 등)에서 시험 일정 확인하고 자격검정 신청하기', 'CHALLENGE', '17');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '강사 이력서 제작하기: 활동 사진/지도 대상/진행시간/사용한 교구 예시 포함', 'CHALLENGE', '17');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '사단법인 세계레크리에이션교육협회에서 “구인/강사/재능” 메뉴 탐색하기', 'CHALLENGE', '17');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “초보 레크리에이션 진행 팁”, “어르신 대상 레크리에이션” 등의 영상 시청해 대상별 진행 스킬 익히기', 'CHALLENGE', '17');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “바리스타 브이로그”, “카페 알바” 검색하여 바리스타 업무 흐름 익히기', 'PREPARE', '18');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 네이버에서 “[지역명] 바리스타 학원”, “커피 아카데미” 검색해 수강 가능한 기관 목록 정리하기', 'PREPARE', '18');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 사람인에서 연령 무관 조건의 카페 알바 검색한뒤 저장해놓기', 'PREPARE', '18');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 고용24에서 “바리스타 과정” 검색 후 내일배움카드 사용 가능한 국비지원 과정 확인하기', 'START', '18');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '실습 커리큘럼, 자격시험 응시 여부 확인한 뒤 바리스타 학원 등록하기', 'START', '18');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '자격시험 대비를 위해 실기 위주 교재 및 커피 추출 기초 도서 구매한 후에 예습하기', 'START', '18');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '‘한국커피협회’과 같은 자격시험 주관 기관 홈페이지에서 시험 일정 확인 후 접수하기', 'CHALLENGE', '18');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 사람인 등에서 “바리스타”, “카페” 키워드로 채용 공고 검색하기', 'CHALLENGE', '18');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '카페 근무하며, 인기 메뉴 레시피, 원두/머신/소모품 브랜드, 매장 회전율, 주방 기구 배치 및 동선, 고객 리뷰 패턴 메모하기', 'CHALLENGE', '18');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “공인중개사 합격수기”, “공인중개사 준비” 검색 후 시청한 뒤 공통된 공부 전략 메모하기', 'PREPARE', '19');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 네이버에서 “[지역명] 공인중개사 학원”, “공인중개사 스터디 모임” 검색하여 비교표 형태로 작성해보기', 'PREPARE', '19');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '자격증의 실제 활용 사례 3건 조사해보기 (예: 중개업 창업 / 임대관리 회사 취업)', 'PREPARE', '19');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '“공인중개사 자격증 신청” 검색 후 접수 일정 확인하기', 'START', '19');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '공인중개사 1차 민법, 2차 공법 기본서 및 기출문제집 구매 후, 주 3회 학습 계획표 작성하기', 'START', '19');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '네이버 카페에서 공인중개사 스터디/학습 동호회 가입하여 정보 얻기', 'START', '19');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '큐넷 웹사이트에서 자격증 시험 접수하기', 'CHALLENGE', '19');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '취업 경로 정리해보기: 창업, 프랜차이즈 가맹, 임대관리 회사 취업 등 경로별 조건 비교해보기', 'CHALLENGE', '19');

INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '유튜브에서 “산후조리사 브이로그”, “산모도우미 일과” 3편 이상 시청 후 공통 업무 메모하기', 'PREPARE', '20');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '네이버에서 “산후조리사 자격증”, “산모신생아 건강관리사” 검색해 비교해본 뒤 자격 기준 확인하기', 'PREPARE', '20');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 네이버에서 “[지역명] 산후조리사 교육기관” 검색해 3곳 찾아보기: 위치, 수강료 수업일정 메모해두기', 'PREPARE', '20');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림 또는 고용24에서 “산후조리사”, “산모신생아 건강관리사” 교육과정 검색 후 내일배움카드를 활용 가능한지 확인하기', 'START', '20');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '교육기관에 전화하여 수업일정, 실습 포함 여부, 수료증/자격증 발급 여부, 취업 연계 유무 항목 반드시 체크하기', 'START', '20');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '산후조리 관련 기초서적 국가자격시험 대비 교재 구매 후 주요 용어와 산모·신생아 케어에 대해 메모하며 공부하기', 'START', '20');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '산후조리사 민간자격시험 또는 산모신생아 건강관리사 관련 인증 과정 등록 및 시험 접수하기', 'CHALLENGE', '20');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '두드림, 사람인, 워크넷 등에서 “산후조리사”, “산모도우미” 키워드로 일자리 검색하여 구인글 최소 5개 찾아보기', 'CHALLENGE','20');
INSERT INTO job_todo (created_at, updated_at, deleted, title, todo_category, job_id) VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '첫 출근 전, 유튜브에서 “초보 산후조리사”, “신생아 케어 기본” 영상 시청하며 실전 대응력 키우기', 'CHALLENGE', '20');
