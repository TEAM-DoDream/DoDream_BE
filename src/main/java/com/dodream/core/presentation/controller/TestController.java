package com.dodream.core.presentation.controller;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.GlobalErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.core.presentation.swagger.TestSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController implements TestSwagger {

    private final DataSource dataSource;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_KEY = "두드림";
    private static final String REDIS_DATA = "화이팅!!";
    
    @Override
    @GetMapping("/health")
    public ResponseEntity<RestResponse<String>> healthCheck() {
        return ResponseEntity.ok(new RestResponse<>("OK"));
    }

    @Override
    @GetMapping("/db")
    public ResponseEntity<RestResponse<String>> dbHealthCheck() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT NOW()")) {

            if (resultSet.next()) {
                return ResponseEntity.ok(
                        new RestResponse<>("DB 연결 성공! 현재 시간: " + resultSet.getString(1))
                );
            } else {
                throw GlobalErrorCode.GET_DATA_ERROR.toException();
            }

        } catch (Exception e) {
            throw GlobalErrorCode.DB_CONNECTION_ERROR.toException();
        }
    }

    @Override
    @PostMapping("/redis/set")
    public ResponseEntity<RestResponse<String>> redisSetDataCheck() {
        try{
            redisTemplate.opsForValue().set(REDIS_KEY, REDIS_DATA);
            return ResponseEntity.ok(new RestResponse<>(REDIS_KEY));
        }catch (DomainException e){
            throw GlobalErrorCode.REDIS_SET_DATA_ERROR.toException();
        }
    }

    @Override
    @GetMapping("/redis/get")
    public ResponseEntity<RestResponse<String>> redisGetDataCheck() {
        try{
            String data = (String) redisTemplate.opsForValue().get(REDIS_KEY);
            return ResponseEntity.ok(new RestResponse<>(REDIS_KEY + " " + data));
        }catch (DomainException e){
            throw GlobalErrorCode.REDIS_GET_DATA_ERROR.toException();
        }
    }
}
