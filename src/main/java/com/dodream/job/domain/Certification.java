package com.dodream.job.domain;

import com.dodream.core.infrastructure.jpa.entity.BaseLongIdEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "certification")
public class Certification extends BaseLongIdEntity {

    @Column(nullable = false, name = "certification_name")
    private String certificationName;

    @Column(nullable = false, name = "certification_preparation_period")
    private String certificationPreparationPeriod;

    @ManyToOne
    @JoinColumn(name = "id")
    private Job job;
}
