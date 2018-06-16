package com.sensei.domain;

public enum CoachingSessionStatus {
	
	INPROGRESS(0),
	COMPLETED(1);
		
    private final int status;

    CoachingSessionStatus(int status) {
        this.status = status;
    }
    
    public int getSatus() {
        return this.status;
    }
    
    public static String getValue(int status)
    {
    	switch (status) {
    	case 0:
			return "Inprogress";
		case 1:
			return "Completed";
		default:
			return "Unknown";
		}
    }

}
