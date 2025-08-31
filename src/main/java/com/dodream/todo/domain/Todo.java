package com.dodream.todo.domain;

import com.dodream.core.infrastructure.cache.annotation.DistributedLock;
import com.dodream.core.infrastructure.jpa.entity.BaseLongIdEntity;
import com.dodream.job.domain.JobTodo;
import com.dodream.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "todo")
public class Todo extends BaseLongIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_group_id", nullable = false)
    private TodoGroup todoGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(name = "save_count", nullable = false)
    @Builder.Default
    private Long saveCount = 0L;

    @Builder.Default
    private Boolean completed = false;
    
    // 0인 경우 내가 작성한 투두, 0이 아닌 경우 다른 사람 투두 저장한거임
    @Column(name = "other_todo_id", nullable = false)
    @Builder.Default
    private Long otherTodoId = 0L;

    public void updateTitle(String title) {
        this.title = title;
    }
    public void updateCompleted() {
        this.completed = !this.completed;
    }

    @DistributedLock(lockName = "todo-save-time")
    public void increaseSaveCount() {
        this.saveCount++;
    }

    @DistributedLock(lockName = "todo-save-time")
    public void decreaseSaveCount() {
        if(this.saveCount > 0)
            this.saveCount--;
    }


    @Builder
    private Todo(TodoGroup todoGroup, Member member, String title) {
        this.todoGroup = todoGroup;
        this.member = member;
        this.title = title;
    }

    public static Todo of(TodoGroup todoGroup, Member member, String title) {
        return Todo.builder()
            .todoGroup(todoGroup)
            .member(member)
            .title(title)
            .build();
    }

    public static Todo of(TodoGroup todoGroup, Member member, JobTodo jobTodo) {
        return Todo.builder()
            .todoGroup(todoGroup)
            .member(member)
            .title(jobTodo.getTitle())
            .build();
    }
}
