package com.dodream.region.domain;

import com.dodream.core.infrastructure.jpa.entity.BaseLongIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "region")
public class Region extends BaseLongIdEntity {

    @Column(name = "region_code", unique = true, nullable = false)
    private String regionCode;

    @Column(name = "region_name", nullable = false)
    private String regionName;

    public static Region of(
            String regionCode,
            String regionName
    ){
        return Region.builder()
                .regionCode(regionCode)
                .regionName(regionName)
                .build();
    }
}
