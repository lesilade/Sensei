package com.sensei.web.rest.vm;

import javax.validation.constraints.NotNull;

public class CoachAvailabilityRequest {

	@NotNull
    private String day;
	@NotNull
    private String timeofday;
	@NotNull
    private String  user;
	
    
    public CoachAvailabilityRequest() {
	}
    
    public CoachAvailabilityRequest(String day, String timeofday, String user) {
		super();
		this.day = day;
		this.timeofday = timeofday;
		this.user = user;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTimeofday() {
		return timeofday;
	}

	public void setTimeofday(String timeofday) {
		this.timeofday = timeofday;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "CoachAvailabilityVM [day=" + day + ", timeofday=" + timeofday + ", user=" + user + "]";
	}
}
