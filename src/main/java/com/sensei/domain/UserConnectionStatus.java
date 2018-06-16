package com.sensei.domain;

public enum UserConnectionStatus 
{
	UNFLLOW(0),
	FOLLOW(1),
	BLOCK(2);
	
    private final int status;

    UserConnectionStatus(int status) {
        this.status = status;
    }
    
    public int getSatus() {
        return this.status;
    }
    
    public static String getValue(int status)
    {
    	switch (status) {
    	case 0:
			return "unfollow";
		case 1:
			return "follow";
		case 2:
			return "block";
		default:
			return "unknown";
		}
    }

}
