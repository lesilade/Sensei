package com.sensei.web.rest.vm;

import com.sensei.domain.CaochingRequest;
import com.sensei.domain.User;

public class CoachDashboardVM 
{
    private Long id;
    private String requestName;
    private String status;
    private CaochingRequest caochingRequest;
    private User user;
    
	public CoachDashboardVM() {
	}
	
	public CoachDashboardVM(Long id, String requestName, String status, CaochingRequest caochingRequest, User user) {
		this.id = id;
		this.requestName = requestName;
		this.status = status;
		this.caochingRequest = caochingRequest;
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRequestName() {
		return requestName;
	}
	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public CaochingRequest getCaochingRequest() {
		return caochingRequest;
	}
	public void setCaochingRequest(CaochingRequest caochingRequest) {
		this.caochingRequest = caochingRequest;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "CoachDashboardVM [id=" + id + ", requestName=" + requestName + ", status=" + status
				+ ", caochingRequest=" + caochingRequest + ", user=" + user + "]";
	}
        
}
