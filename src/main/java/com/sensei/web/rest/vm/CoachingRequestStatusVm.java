package com.sensei.web.rest.vm;

import javax.validation.constraints.NotNull;

public class CoachingRequestStatusVm {
	
	
	@NotNull
	private Long coachingRequestId;
	@NotNull
	private String username;
	@NotNull
	private Boolean isAccepted;
	
	
	public CoachingRequestStatusVm() {

	}
	
	public CoachingRequestStatusVm(Long coachingRequestId, String username, Boolean isAccepted) 
	{
		super();
		this.coachingRequestId = coachingRequestId;
		this.username = username;
		this.isAccepted = isAccepted;
	}
	
	public Long getCoachingRequestId() {
		return coachingRequestId;
	}
	public void setCoachingRequestId(Long coachingRequestId) {
		this.coachingRequestId = coachingRequestId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getIsAccepted() {
		return isAccepted;
	}
	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	
	
}
