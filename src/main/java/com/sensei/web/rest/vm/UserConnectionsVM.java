package com.sensei.web.rest.vm;

import javax.validation.constraints.NotNull;

public class UserConnectionsVM {
	
    private String status;

    @NotNull
    private String connectionUsername;
  
	public UserConnectionsVM() 
	{
	}

	public UserConnectionsVM(String status, String connectionUsername) {
		this.status = status;
		this.connectionUsername = connectionUsername;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getConnectionUsername() {
		return connectionUsername;
	}

	public void setConnectionUsername(String connectionUsername) {
		this.connectionUsername = connectionUsername;
	}

	@Override
	public String toString() {
		return "UserConnectionsVM [status=" + status + ", connectionUsername=" + connectionUsername + "]";
	}

}
