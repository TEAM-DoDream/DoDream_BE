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
    private String title;

    @Builder.Default
    private Boolean completed = false;

    @Builder.Default
    private Boolean isPublic = false;

    @Column(length = 5000)
    private String memoText;

    private String link;

    @Builder.Default
    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TodoImage> images = new ArrayList<>();

    public void updateTitle(String title) {
        this.title = title;
    }
    public void updateMemoText(String memoText) {
        this.memoText = memoText;
    }
    public void updateLink(String link) {
        this.link= link;
    }
    public void updateCompleted() {
        this.completed = !this.completed;
    }
    public void updateIsPublic() {
        this.isPublic = !this.isPublic;
    }
    public void initialIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }


    @Builder
    private Todo(TodoGroup todoGroup, Member member, String title, String memoText, String link,
        Boolean isPublic) {
        this.todoGroup = todoGroup;
        this.member = member;
        this.title = title;
        this.memoText = memoText;
        this.link = link;
        this.isPublic = isPublic;
    }

    public static Todo of(TodoGroup todoGroup, Member member, String title, String memoText,
        String link, Boolean isPublic) {
        return Todo.builder()
            .todoGroup(todoGroup)
            .member(member)
            .title(title)
            .memoText(memoText)
            .link(link)
            .isPublic(isPublic)
            .build();
    }

    public static Todo of(TodoGroup todoGroup, Member member, JobTodo jobTodo) {
        return Todo.builder()
            .todoGroup(todoGroup)
            .member(member)
            .title(jobTodo.getTitle())
            .memoText(null)
            .link(null)
            .isPublic(true)
            .build();
    }
}
