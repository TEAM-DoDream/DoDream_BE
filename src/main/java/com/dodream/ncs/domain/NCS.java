package com.dodream.ncs.domain;

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
@Table(name = "ncs")
public class NCS extends BaseLongIdEntity {

    @Column(name = "ncs_code", nullable = false, unique = true)
    private String ncsCode;

    @Column(name = "ncs_name", nullable = false)
    private String ncsName;

    public static NCS of(String ncsCode, String ncsName) {
        return NCS.builder()
                .ncsCode(ncsCode)
                .ncsName(ncsName)
                .build();
    }
}
