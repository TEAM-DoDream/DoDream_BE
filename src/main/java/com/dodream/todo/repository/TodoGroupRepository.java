package com.dodream.todo.repository;

import com.dodream.member.domain.Member;
import com.dodream.todo.domain.TodoGroup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoGroupRepository extends JpaRepository<TodoGroup, Long> {

    Optional<TodoGroup> findByIdAndMember(Long id, Member member);

}
