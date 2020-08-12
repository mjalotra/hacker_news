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
public class Comment {
	private String by;
	@JsonIgnore
	private Long id;
	private List<String> kids;
	@JsonIgnore
	private Long parent;
	private Date time;
	private String text;
	private String type;
	
	public int getKidsCount() {
		if(this.kids!=null) {
			return this.kids.size();	
		}
		return 0;
	}
}
