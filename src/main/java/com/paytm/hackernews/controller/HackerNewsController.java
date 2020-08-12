package com.paytm.hackernews.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.paytm.hackernews.config.HackerNewsConfig;
import com.paytm.hackernews.model.Comment;
import com.paytm.hackernews.model.Item;
import com.paytm.hackernews.service.IHackerNewsService;

import io.swagger.annotations.ApiOperation;
import j8.FilterMap.Employee;
import reactor.core.publisher.Flux;

@RestController
//@RequestMapping("/hacker-news")
public class HackerNewsController {
	
	@Autowired 
	private WebClient webClient;
	
	@Autowired
	private HackerNewsConfig hackerNewsConfig;
	
	@Autowired
	private IHackerNewsService hackerNewsService;
	
	private Date currentDate;
	
	@GetMapping("/top-stories")
	@ApiOperation(value = "print report of collections", produces = "application/json")
	public List<Item> getNews() throws URISyntaxException{
		List<Long> longFlux = webClient.get().uri("topstories.json").retrieve().bodyToFlux(Long.class).collectList().block();
		Flux<Item> list = Flux.fromIterable(longFlux).flatMap(
				id-> webClient.get().uri("item/{id}.json",id)
				.retrieve().bodyToFlux(Item.class)
				);
		if(currentDate==null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -10);
			currentDate = cal.getTime();
		}
		List<Item> items = list.collectList().block().stream()
		.filter(item ->"story".equalsIgnoreCase(item.getType()))
		.filter(item -> item.getTime().compareTo(currentDate)<0)
		.filter(Objects::nonNull).sorted(Comparator.comparing(Item::getScore, Comparator.comparing(Math::abs)).reversed())
		.limit(10)
		  .collect(Collectors.toList());
		return items;
	}
	
	
	
	
	@GetMapping("/comments")
	@ApiOperation(value = "print report of collections", produces = "application/json")
	public Flux<Item> getComments() throws URISyntaxException{
		List<Long> longFlux = webClient.get().uri("topstories.json").retrieve().bodyToFlux(Long.class).collectList().block();
		Flux<Item> list = Flux.fromIterable(longFlux).flatMap(
				id-> webClient.get().uri("item/{id}.json",id)
				.retrieve().bodyToFlux(Item.class)
				);
		if(currentDate==null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -10);
			currentDate = cal.getTime();
		}
		List<Item> items = list.collectList().block();
		List<Long> kids = new ArrayList<>();
		if(!CollectionUtils.isEmpty(items)) {
			kids = items.stream().flatMap(item -> item.getKids().stream()).collect(Collectors.toList());
		}
		Flux<Comment> commentFlux = Flux.fromIterable(kids).flatMap(
					id-> webClient.get().uri("item/{id}.json",id)
					.retrieve().bodyToFlux(Comment.class)
					);
		List<Comment> comments = commentFlux.collectList().block();
		comments.stream().sorted(Comparator.comparing(Comment::getKidsCount , Comparator.comparing(Math::abs)).reversed()).limit(10)
		.collect(Collectors.toList());
		return list;
	}
	

	/*
	 * StringBuilder uriStringBuilder = new
	 * StringBuilder(hackerNewsConfig.getBaseURI());
	 * uriStringBuilder.append("topstories.json"); String s=
	 * restTemplate.getForObject(new URI(uriStringBuilder.toString()),
	 * String.class); System.out.println(s);
	 */
}
 