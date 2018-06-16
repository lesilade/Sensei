package com.sensei.domain;

public enum RequestMatchStatus {

	PENDING(0),
	ACCEPT(1),
	DECLINE(2),
	CANTACCEPT(3);
	
    private final int status;

    RequestMatchStatus(int status) {
        this.status = status;
    }
    
    public int getSatus() {
        return this.status;
    }
    
    public static String getValue(int status)
    {
    	switch (status) {
    	case 0:
			return "pending";
		case 1:
			return "accept";
		case 2:
			return "decline";
		case 3:
			return "cantaccept";
		default:
			return "unknown";
		}
    }
}
