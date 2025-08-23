package com.dodream.todo.repository;

import com.dodream.job.domain.QJob;
import com.dodream.member.domain.QMember;
import com.dodream.todo.domain.QTodo;
import com.dodream.todo.domain.QTodoGroup;
import com.dodream.todo.dto.response.TodoCommunityResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TodoCommunityResponse> findByTop5SaveTodo(String jobName) {
        QTodo todo = QTodo.todo;
        QTodoGroup todoGroup = QTodoGroup.todoGroup;
        QJob job = QJob.job;
        QMember member = QMember.member;


        return queryFactory
                .select(Projections.constructor(
                        TodoCommunityResponse.class,
                        todo.id,
                        member.nickName,
                        member.level,
                        member.profileImage,
                        todo.createdAt,
                        todo.title,
                        todo.saveCount
                ))
                .from(todo)
                .join(todo.todoGroup, todoGroup)
                .join(todoGroup.job, job)
                .join(todo.member, member)
                .where(job.jobName.eq(jobName))
                .orderBy(todo.saveCount.desc())
                .limit(5)
                .fetch();
    }
}
