package com.dodream.todo.repository;

import com.dodream.todo.domain.TodoImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoImageRepository extends JpaRepository<TodoImage,Long> {

}
