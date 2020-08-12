package com.paytm.hackernews.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

	@Value("${redis.hostName:localhost}")
	private String hostName;
	
	@Value("${redis.port:6379}")
	private Integer port;
		
	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = null;
		try {
			RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(hostName, port);
			jedisConnectionFactory = new JedisConnectionFactory(standaloneConfiguration);
		} catch (RedisConnectionFailureException e) {
			e.printStackTrace();
		}
		return jedisConnectionFactory;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	    final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
	    template.setConnectionFactory(redisConnectionFactory());
	    template.setEnableTransactionSupport(true);
	    return template;
	}
}
