package com.sensei.web.rest.vm;

import javax.validation.constraints.NotNull;

public class NewsFeedVmRequest {
	
	private String title;
	private String imageUrl;
	@NotNull
	private String content;
	@NotNull
	private String login;
	
	
	public NewsFeedVmRequest() {
	}

	public NewsFeedVmRequest(String title, String imageUrl, String content, String login) {
		super();
		this.title = title;
		this.imageUrl = imageUrl;
		this.content = content;
		this.login = login;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	@Override
	public String toString() {
		return "NewsFeedVmRequest [title=" + title + ", imageUrl=" + imageUrl + ", content=" + content + ", login="
				+ login + "]";
	}
	
	
}
