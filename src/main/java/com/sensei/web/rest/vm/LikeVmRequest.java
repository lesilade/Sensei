package com.sensei.web.rest.vm;

import javax.validation.constraints.NotNull;

public class LikeVmRequest {
	
	@NotNull
	private String login;
	
	@NotNull
    private Long newsfeedId;
	
    public LikeVmRequest() {
	}

	public LikeVmRequest(String login, Long newsfeedId) {
		super();
		this.login = login;
		this.newsfeedId = newsfeedId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Long getNewsfeedId() {
		return newsfeedId;
	}

	public void setNewsfeedId(Long newsfeedId) {
		this.newsfeedId = newsfeedId;
	}

	@Override
	public String toString() {
		return "LikeVmRequest [login=" + login + ", newsfeedId=" + newsfeedId + "]";
	}
	
    
}
