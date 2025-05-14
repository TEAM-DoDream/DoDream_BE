package com.dodream.job.repository;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.PhysicalActivity;
import com.dodream.job.domain.Require;
import com.dodream.job.domain.WorkTime;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobSpecification {

    public static Specification<Job> matchesFilter(String require, String workTime, String physical){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(require != null && !require.isBlank()){
                Require requireEnum = Arrays.stream(Require.values())
                        .filter(r -> r.getDescription().equals(require))
                        .findFirst()
                        .orElse(null);

                if(requireEnum != null){
                    predicates.add(cb.equal(root.get("requiresCertification"), requireEnum));
                }
            }

            if(physical != null && !physical.isBlank()){
                PhysicalActivity physicalEnum  = Arrays.stream(PhysicalActivity.values())
                        .filter(e -> e.getDescription().equals(physical))
                        .findFirst()
                        .orElse(null);

                System.out.println("physical = " + physical);
                System.out.println("매핑된 physicalEnum = " + physicalEnum);

                if (physicalEnum != null) {
                    predicates.add(cb.equal(root.get("physicalActivityLevel"), physicalEnum));
                }
            }

            if(workTime != null && !workTime.isBlank()){
                WorkTime workTimeEnum = Arrays.stream(WorkTime.values())
                        .filter(w -> w.getDescription().equals(workTime))
                        .findFirst().orElse(null);

                if(workTimeEnum != null){
                    predicates.add(cb.equal(root.get("workTimeSlot"), workTimeEnum));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
