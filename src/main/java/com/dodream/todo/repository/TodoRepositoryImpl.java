package com.dodream.todo.repository;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.QJob;
import com.dodream.member.domain.Level;
import com.dodream.member.domain.QMember;
import com.dodream.todo.domain.QTodo;
import com.dodream.todo.domain.QTodoGroup;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.dto.response.TodoCommunityResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                        todo.todoGroup.id,
                        member.nickName,
                        member.level,
                        member.profileImage,
                        todo.createdAt,
                        todo.title,
                        todo.saveCount,
                        Expressions.constant(false)
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

        if ("인기순".equalsIgnoreCase(sort.trim())) {
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

    @Override
    public Job findJobWithMostTodos() {
        QTodo todo = QTodo.todo;
        QTodoGroup todoGroup = QTodoGroup.todoGroup;

        return queryFactory.select(todoGroup.job)
                .select(todoGroup.job)
                .from(todo)
                .join(todo.todoGroup, todoGroup)
                .groupBy(todoGroup.job)
                .orderBy(todo.id.count().desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Todo findRandomTodo(String jobName) {
        QTodo todo = QTodo.todo;
        QTodoGroup todoGroup = QTodoGroup.todoGroup;

        JPQLQuery<Long> subQueryMaxId;

        if (jobName != null) {
            subQueryMaxId = JPAExpressions
                    .select(todo.id.max())
                    .from(todo)
                    .join(todo.todoGroup, todoGroup)
                    .where(todoGroup.job.jobName.eq(jobName));
        } else {
            subQueryMaxId = JPAExpressions
                    .select(todo.id.max())
                    .from(todo);
        }

        NumberExpression<Long> randomId = Expressions.numberTemplate(Long.class,
                "FLOOR(1 + ({0}) * RAND())", subQueryMaxId);

        return queryFactory
                .selectFrom(todo)
                .join(todo.todoGroup, todoGroup)
                .where(todo.id.goe(randomId),
                        jobNameEquals(jobName))
                .orderBy(todo.id.asc())
                .limit(1)
                .fetchOne();
    }

    private BooleanExpression jobNameEquals(String jobName) {
        return jobName == null ? null : QTodo.todo.todoGroup.job.jobName.eq(jobName);
    }
}
