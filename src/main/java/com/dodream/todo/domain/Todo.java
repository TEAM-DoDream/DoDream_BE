package com.dodream.todo.domain;

import com.dodream.core.infrastructure.jpa.entity.BaseLongIdEntity;
import com.dodream.job.domain.JobTodo;
import com.dodream.job.domain.TodoCategory;
import com.dodream.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private TodoCategory todoCategory;

    @Column(nullable = false)
    private String title;

    @Builder.Default
    private Boolean completed = false;

    @Builder.Default
    private Boolean isPublic = false;

    private String memoText;

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TodoImage> images = new ArrayList<>();

    public void updateDeleted() {
        this.deleted = true;
    }

    public void updateCompleted() {
        this.completed = !this.completed;
    }

    public void updateIsPublic() {
        this.isPublic = !this.isPublic;
    }

//    @Builder
//    private Todo(TodoGroup todoGroup, Member member, String title, TodoCategory todoCategory) {
//        this.todoGroup = todoGroup;
//        this.member = member;
//        this.title = title;
//        this.todoCategory = todoCategory;
//    }

    public static Todo of(TodoGroup todoGroup, Member member, JobTodo jobTodo) {
        return Todo.builder()
            .todoGroup(todoGroup)
            .member(member)
            .title(jobTodo.getTitle())
            .todoCategory(jobTodo.getTodoCategory())
            .build();
    }

    public static Todo of(TodoGroup todoGroup, Member member, String title,
        TodoCategory todoCategory) {
        return Todo.builder()
            .todoGroup(todoGroup)
            .member(member)
            .title(title)
            .todoCategory(todoCategory)
            .build();
    }
}
