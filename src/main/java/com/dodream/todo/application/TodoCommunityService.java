package com.dodream.todo.application;

import com.dodream.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoCommunityService {

    private final TodoRepository todoRepository;

    // 다른 사람의 투두를 저장한다.

    // 다른 사람의 투두 저장을 취소한다.

    // 현재 직업의 투두 중 저장횟수가 가장 높은 투두를 5개 출력한다.

    // 투두 필터에 따른 투두 목록을 출력한다(무한 스크롤)
}
