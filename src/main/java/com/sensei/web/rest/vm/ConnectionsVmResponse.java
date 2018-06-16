package com.sensei.web.rest.vm;

import com.sensei.domain.User;

public class ConnectionsVmResponse {
	
	private Long Id;
	private Long usertwoId;
	private String status;
	private User user;
	
	public ConnectionsVmResponse() {
	}
	
	public ConnectionsVmResponse(Long id, Long usertwoId, String status, User user) {
		this.Id = id;
		this.usertwoId = usertwoId;
		this.status = status;
		this.user = user;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
	
	public Long getUsertwoId() {
		return usertwoId;
	}

	public void setUsertwoId(Long usertwoId) {
		this.usertwoId = usertwoId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "ConnectionsVmResponse [Id=" + Id + ", usertwoId=" + usertwoId + ", status=" + status + ", user=" + user
				+ "]";
	}
	
}