package com.dodream.core.presentation.controller;

import com.dodream.core.exception.GlobalErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.core.presentation.swagger.TestSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
