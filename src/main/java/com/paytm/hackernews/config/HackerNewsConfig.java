package com.paytm.hackernews.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HackerNewsConfig {
	
//	@Value("${hacker-news.baseUri}")
//	private String baseURI;
//	
//	public String getBaseURI() {
//		return baseURI;
//	}
//
//	public void setBaseURI(String baseURI) {
//		this.baseURI = baseURI;
//	}

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
	public WebClient webClient() {
		 return WebClient.builder()
         .baseUrl("https://hacker-news.firebaseio.com/v0/").defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
         .build();

	}
}
