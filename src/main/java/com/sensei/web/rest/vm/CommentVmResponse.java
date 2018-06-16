package com.sensei.web.rest.vm;

import java.time.LocalDate;

public class CommentVmResponse {

    private Long id;
    private String content;
    private String datePosted;
    private Long newsfeedId;
	
    
    public CommentVmResponse() {
	}


	public CommentVmResponse(Long id, String content, String datePosted, Long newsfeedId) {
		super();
		this.id = id;
		this.content = content;
		this.datePosted = datePosted;
		this.newsfeedId = newsfeedId;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getDatePosted() {
		return datePosted;
	}


	public void setDatePosted(String string) {
		this.datePosted = string;
	}


	public Long getNewsfeedId() {
		return newsfeedId;
	}


	public void setNewsfeedId(Long newsfeedId) {
		this.newsfeedId = newsfeedId;
	}


	@Override
	public String toString() {
		return "CommentVmResponse [id=" + id + ", content=" + content + ", datePosted=" + datePosted + ", newsfeedId="
				+ newsfeedId + "]";
	}
	
    
}
