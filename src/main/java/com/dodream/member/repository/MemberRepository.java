package com.dodream.member.repository;

import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdAndState(Long id, State state);

    Optional<Member> findByNickNameAndState(String nickName, State state);

    Optional<Member> findByMemberIdAndState(String memberId, State state);

    boolean existsByMemberIdAndState(String memberId, State state);

    boolean existsByNickNameAndState(String nickName, State state);
}
