package com.sensei.web.rest.vm;

import com.sensei.domain.CaochingRequest;
import com.sensei.domain.User;

public class CoachingSessionVmResponse {
	

    private Long id;
    private String title;
    private String status;
    private String role;
    private String startDate;
    private String endDate;
    private User user;
    private CaochingRequest caochingRequest;
    private boolean isActive;
    
    
	public CoachingSessionVmResponse() {
	}
	
	
	public CoachingSessionVmResponse(Long id, String title, String status, String role, String startDate,
			String endDate, User user, CaochingRequest caochingRequest, boolean isActive) {
		super();
		this.id = id;
		this.title = title;
		this.status = status;
		this.role = role;
		this.startDate = startDate;
		this.endDate = endDate;
		this.user = user;
		this.caochingRequest = caochingRequest;
		this.isActive = isActive;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public CaochingRequest getCaochingRequest() {
		return caochingRequest;
	}
	public void setCaochingRequest(CaochingRequest caochingRequest) {
		this.caochingRequest = caochingRequest;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "CoachingSessionVmResponse [id=" + id + ", title=" + title + ", status=" + status + ", role=" + role
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", user=" + user + ", caochingRequest="
				+ caochingRequest + ", isActive=" + isActive + "]";
	}
    
}
