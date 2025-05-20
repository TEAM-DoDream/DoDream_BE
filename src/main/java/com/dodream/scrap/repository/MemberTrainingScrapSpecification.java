package com.dodream.scrap.repository;

import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class MemberTrainingScrapSpecification {

    public static Specification<MemberTrainingScrap> matchesFilter(Long memberId, String locName){
        return (root, query, cb) -> {

            Predicate predicates = cb.equal(root.get("member").get("id"), memberId);

            if (locName != null && !locName.isBlank()) {
                predicates = cb.and(predicates,
                        cb.like(root.get("trainingOrgAddr"), "%" + locName + "%"));
            }

            return predicates;
        };
    }
}
