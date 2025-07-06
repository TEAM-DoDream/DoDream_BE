package com.dodream.scrap.repository.training;

import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import com.dodream.scrap.domain.entity.QMemberRecruitScrap;
import com.dodream.scrap.domain.entity.QMemberTrainingScrap;
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
public class MemberTrainingScrapRepositoryImpl implements MemberTrainingScrapRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MemberTrainingScrap> searchWithFilter(Long memberId, String locName, String sortBy, int pageSize, int pageNum){
        QMemberTrainingScrap qMemberTrainingScrap = QMemberTrainingScrap.memberTrainingScrap;
        Pageable pageable = getPageable(pageNum, pageSize, sortBy);

        List<MemberTrainingScrap> content = queryFactory
                .selectFrom(qMemberTrainingScrap)
                .where(
                        eqId(memberId),
                        eqLocName(locName)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(resolveSort(qMemberTrainingScrap, sortBy))
                .fetch();

        Long count = queryFactory
                .select(qMemberTrainingScrap.count())
                .from(qMemberTrainingScrap)
                .where(
                        eqId(memberId),
                        eqLocName(locName)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, count != null ? count : 0);
    }

    private BooleanExpression eqId(Long memberId) {
        return memberId != null ? QMemberTrainingScrap.memberTrainingScrap.member.id.eq(memberId) : null;
    }

    private BooleanExpression eqLocName(String locName) {
        return StringUtils.hasText(locName) ? QMemberTrainingScrap.memberTrainingScrap.trainingOrgAddr.eq(locName) : null;
    }

    private Pageable getPageable(int pageNum, int pageSize, String sortBy){
        return PageRequest.of(
                pageNum,
                pageSize,
                SortBy.fromName(sortBy).getSort()
        );
    }

    private OrderSpecifier<?>[] resolveSort(QMemberTrainingScrap q, String sortByName) {
        Sort sort = SortBy.fromName(sortByName).getSort();
        PathBuilder<MemberTrainingScrap> path = new PathBuilder<>(MemberTrainingScrap.class, q.getMetadata());

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
