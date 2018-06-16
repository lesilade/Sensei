package com.sensei.web.rest.vm;

import com.sensei.domain.User;

public class UserConnectionsVMResponse 
{
	private Long id;
    private String status;
    private User user;
    private User connectionUser;
    private String connectionType;
	
    public UserConnectionsVMResponse() {
	}

	public UserConnectionsVMResponse(Long id, String status, User user, User connectionUser,
			String connectionType) {
		this.id = id;
		this.status = status;
		this.user = user;
		this.connectionUser = connectionUser;
		this.connectionType = connectionType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public User getConnectionUser() {
		return connectionUser;
	}

	public void setConnectionUser(User connectionUser) {
		this.connectionUser = connectionUser;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	@Override
	public String toString() {
		return "UserConnectionsVMResponse [id=" + id + ", status=" + status + ", user=" + user
				+ ", connectionUser=" + connectionUser + ", connectionType=" + connectionType + "]";
	}
	
}
