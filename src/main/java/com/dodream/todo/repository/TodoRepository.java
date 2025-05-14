package com.dodream.todo.repository;

import com.dodream.member.domain.Member;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByIdAndMemberAndDeletedIsFalse(Long todoId, Member member);

    @Query("SELECT t FROM Todo t WHERE t.todoGroup = :group AND t.deleted = false ORDER BY t.createdAt DESC")
    List<Todo> findTop2ByTodoGroup(@Param("group") TodoGroup group, Pageable pageable);

    @Query("SELECT COUNT(t) FROM Todo t WHERE t.todoGroup = :group AND t.deleted = false")
    Long countByTodoGroup(@Param("group") TodoGroup group);


}
