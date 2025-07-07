package com.dodream.scrap.repository.recruit;

import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.dodream.scrap.domain.entity.QMemberRecruitScrap;
import com.dodream.scrap.domain.value.SortBy;
import com.dodream.scrap.exception.ScrapErrorCode;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRecruitScrapRepositoryImpl implements MemberRecruitScrapRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MemberRecruitScrap> searchWithFilter(Long memberId, String locName, String sortBy, int pageSize, int pageNum) {
        QMemberRecruitScrap qMemberRecruitScrap = QMemberRecruitScrap.memberRecruitScrap;
        Pageable pageable = getPageable(pageNum, pageSize, sortBy);

        List<MemberRecruitScrap> content = queryFactory
                .selectFrom(qMemberRecruitScrap)
                .where(
                    eqId(memberId),
                    eqLocName(locName)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(resolveSort(qMemberRecruitScrap, sortBy))
                .fetch();

        Long count = queryFactory
                .select(qMemberRecruitScrap.count())
                .from(qMemberRecruitScrap)
                .where(
                        eqId(memberId),
                        eqLocName(locName)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, count != null ? count : 0);
    }

    @Override
    public List<String> findScrapedRecruitId(Long memberId, List<String> recruitId) {
        QMemberRecruitScrap qMemberRecruitScrap = QMemberRecruitScrap.memberRecruitScrap;

        return queryFactory.select(qMemberRecruitScrap.recruitId)
                .from(qMemberRecruitScrap)
                .where(
                        qMemberRecruitScrap.member.id.eq(memberId)
                        .and(qMemberRecruitScrap.recruitId.in(recruitId))
                )
                .fetch();
    }

    private BooleanExpression eqId(Long memberId) {
        return memberId != null ? QMemberRecruitScrap.memberRecruitScrap.member.id.eq(memberId) : null;
    }

    private BooleanExpression eqLocName(String locName) {
        return StringUtils.hasText(locName) ? QMemberRecruitScrap.memberRecruitScrap.locationName.eq(locName) : null;
    }

    private Pageable getPageable(int pageNum, int pageSize, String sortBy){
        return PageRequest.of(
                pageNum,
                pageSize,
                SortBy.fromName(sortBy).getSort()
        );
    }

    private OrderSpecifier<?>[] resolveSort(QMemberRecruitScrap q, String sortByName) {
        Sort sort = SortBy.fromName(sortByName).getSort();
        PathBuilder<MemberRecruitScrap> path = new PathBuilder<>(MemberRecruitScrap.class, q.getMetadata());

        return sort.stream()
                .map(order -> {
                    String property = order.getProperty();

                    if (property.equals("createdAt")) {
                        return order.isAscending()
                                ? path.getComparable(property, Comparable.class).asc()
                                : path.getComparable(property, Comparable.class).desc();
                    }

                    throw ScrapErrorCode.SORT_NAME_ERROR.toException();
                })
                .toArray(OrderSpecifier[]::new);
    }
}
