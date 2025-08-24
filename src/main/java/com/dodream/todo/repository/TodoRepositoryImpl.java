package com.dodream.todo.repository;

import com.dodream.job.domain.QJob;
import com.dodream.member.domain.Level;
import com.dodream.member.domain.QMember;
import com.dodream.todo.domain.QTodo;
import com.dodream.todo.domain.QTodoGroup;
import com.dodream.todo.dto.response.TodoCommunityResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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

    @Override
    public Slice<TodoCommunityResponse> findTodosWithSlice(Long memberId, String jobName, Level level, String sort, Pageable pageable) {

        QTodo todo = QTodo.todo;
        QTodoGroup todoGroup = QTodoGroup.todoGroup;
        QJob job = QJob.job;
        QMember member = QMember.member;

        List<Long> mySavedTodoIds = Collections.emptyList();
        if (memberId != null) {
            mySavedTodoIds = findMyTodoIds(memberId);
        }

        List<TodoCommunityResponse> content = queryFactory
                .select(Projections.constructor(
                        TodoCommunityResponse.class,
                        todo.id,
                        member.nickName,
                        member.level,
                        member.profileImage,
                        todo.createdAt,
                        todo.title,
                        todo.saveCount,
                        todo.id.in(mySavedTodoIds)
                ))
                .from(todo)
                .join(todo.todoGroup, todoGroup)
                .join(todoGroup.job, job)
                .join(todo.member, member)
                .where(job.jobName.eq(jobName), levelEq(level))
                .orderBy(sortBy(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) {
            content.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression levelEq(Level level) {
        if (level == null) {
            return null;
        }
        return QMember.member.level.eq(level);
    }

    private OrderSpecifier<?> sortBy(String sort) {
        QTodo todo = QTodo.todo;

        if ("인기 순".equalsIgnoreCase(sort)) {
            return todo.saveCount.desc();
        }
        // 기본값: 최신순 (생성일 내림차순)
        return todo.createdAt.desc();
    }

    @Override
    public List<Long> findMyTodoIds(Long memberId) {
        QTodo todo = QTodo.todo;
        return queryFactory
                .select(todo.otherTodoId)
                .from(todo)
                .where(todo.member.id.eq(memberId))
                .fetch();
    }
}
