package com.sensei.domain;

public enum TimeOfDay {

	MORNING(1),
	AFTERNOON(2),
	EVENING(3);
	
    private final int timeCode;

	TimeOfDay(int timeCode) {
        this.timeCode = timeCode;
    }
    
    public int getTimeCode() {
        return this.timeCode;
    }
    
    public static String getValue(int timecode)
    {
    	switch (timecode) {
		case 1:
			return "morning";
		case 2:
			return "afternoon";
		case 3:
			return "evening";
		default:
			return "unknown";
		}
    }
}
