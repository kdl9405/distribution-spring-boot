package com.aws.distribution.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing //JPA Auditing 활성화  -> 테스트를 위해 config패키지의 JpaConfig로 이동
@SpringBootApplication // 스프링부트의 자동설정, 스프링 Bean 읽기와 생성 자동설정
public class Application { // 메인클래스
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 내장 WAS 실행 -> 톰캣 설치 필요 X (spring boot 내장 was 권장)
    }
}
