package com.knu.noticesender.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * 애플리케이션 기본 TimeZone 설정 클래스
 * 
 * Spring 애플리케이션 시작 시 JVM 기본 시간대를 Aisa/Seoul로 설정
 * 
 * 로그 출력, 날짜/시간 처리, JSON 직렬화, 데이터베이스 저장 시간등이 한국 표준시 기준으로 동작 
 */

@Configuration
@Slf4j
public class timeZoneConfig {

    @PostConstruct
    void setTimeZoneSeoul() {
        log.info("time zone(Asia/Seoul) setting complete");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
