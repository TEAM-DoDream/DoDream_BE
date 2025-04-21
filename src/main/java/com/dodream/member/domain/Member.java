package com.dodream.member.domain;

import com.dodream.core.infrastructure.jpa.entity.BaseLongIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "member")
public class Member extends BaseLongIdEntity {

    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false)
    private String name;
}
