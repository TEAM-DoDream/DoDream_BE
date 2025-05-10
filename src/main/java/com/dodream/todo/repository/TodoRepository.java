package com.dodream.todo.repository;

import com.dodream.member.domain.Member;
import com.dodream.todo.domain.Todo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByIdAndMemberAndDeletedIsFalse(Long todoId, Member member);

}
