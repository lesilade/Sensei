package com.sensei.web.rest.vm;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CommentVm {

    private Long id;
    private String content;
    private String datePosted;
	
    
    public CommentVm() {
	}
    
	public CommentVm(Long id, String content, String datePosted) {
		super();
		this.id = id;
		this.content = content;
		this.datePosted = datePosted;
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

	public void setDatePosted(String datePosted) {
		this.datePosted = datePosted;
	}

	@Override
	public String toString() {
		return "CommentVm [id=" + id + ", content=" + content + ", datePosted=" + datePosted + "]";
	}
    	
}
