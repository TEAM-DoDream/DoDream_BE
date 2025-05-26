package com.dodream.todo.repository;

import com.dodream.job.domain.Job;
import com.dodream.member.domain.Member;
import com.dodream.todo.domain.TodoGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoGroupRepository extends JpaRepository<TodoGroup, Long> {

    Optional<TodoGroup> findByIdAndMember(Long id, Member member);

    Optional<TodoGroup> findByIdAndMemberNot(Long id, Member member);

    List<TodoGroup> findAllByMember(Member member);

    Optional<TodoGroup> findFirstByMemberOrderByIdAsc(Member member);

    Optional<TodoGroup> findTopByMemberOrderByTotalViewDesc(Member member);

    boolean existsByMemberAndJob(Member member, Job job);

    @Query("SELECT tg FROM TodoGroup tg " +
        "WHERE tg.member <> :currentMember " +
        "AND tg.job = :job " +
        "AND tg.deleted = false")
    List<TodoGroup> findTop3ByJobAndNotMember(@Param("job") Job job,
        @Param("currentMember") Member currentMember, Pageable pageable);

    @Query("SELECT tg FROM TodoGroup tg " +
        "WHERE tg.member <> :currentMember " +
        "AND tg.job = :job " +
        "AND tg.deleted = false")
    Page<TodoGroup> findTop10ByJobAndNotMember(@Param("job") Job job,
        @Param("currentMember") Member currentMember, Pageable pageable);

    List<TodoGroup> findTop3ByMemberOrderByTotalViewDesc(Member member);

    List<TodoGroup> findTop5ByJobAndMemberNot(Job job, Member member);

    List<TodoGroup> deleteByMemberAndJobIdIn(Member member,List<Long> jobId);

    void deleteAllByMember(Member member);


}
