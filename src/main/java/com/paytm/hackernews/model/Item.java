package com.paytm.hackernews.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Item {
	private String by;
	@JsonIgnore
	private Long descendants;
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private List<Long> kids;
	private Long score;
	private Date time;
	private String title;
	private String type;
	private String url;
	
	
}
