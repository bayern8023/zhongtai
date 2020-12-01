package com.cmcc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@MapperScan("com.cmcc.infrastructure.database.mapper")
@EnableEurekaClient
@EnableRedisHttpSession
@EnableCircuitBreaker
public class MeetingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingApplication.class, args);
	}
	
}