package com.paytm.hackernews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class HackerNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackerNewsApplication.class, args);
	}

}
