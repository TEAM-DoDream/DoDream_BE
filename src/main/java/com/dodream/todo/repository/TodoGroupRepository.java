package com.dodream.todo.repository;

import com.dodream.todo.domain.TodoGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoGroupRepository extends JpaRepository<TodoGroup, Long> {

}
