package com.sensei.web.rest.vm;

public class ConnectionsVmRequest {
	
	private Long usertwoId;
	private Long currentUserId;
	
	
	
	public ConnectionsVmRequest() {
	}


	public ConnectionsVmRequest(Long usertwoId, Long currentUserId) {
		super();
		this.usertwoId = usertwoId;
		this.currentUserId = currentUserId;
	}


	public Long getUsertwoId() {
		return usertwoId;
	}


	public void setUsertwoId(Long usertwoId) {
		this.usertwoId = usertwoId;
	}


	public Long getCurrentUserId() {
		return currentUserId;
	}


	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}

	@Override
	public String toString() {
		return "connectionsVm [usertwoId=" + usertwoId + ", currentUserId=" + currentUserId
				+ "]";
	}
	
}