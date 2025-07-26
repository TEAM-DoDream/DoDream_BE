package com.dodream.member.repository;

import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdAndState(Long id, State state);

    Optional<Member> findByNickNameAndState(String nickName, State state);

    Optional<Member> findByLoginIdAndState(String loginId, State state);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndLoginId(String email, String loginId);

    boolean existsByEmailAndState(String email, State state);

    boolean existsByLoginIdAndState(String loginId, State state);

    boolean existsByNickNameAndState(String nickName, State state);

    boolean existsByEmail(String email);

    boolean existsByEmailAndLoginId(String email, String loginId);
}
