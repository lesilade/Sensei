package com.sensei.web.rest.vm;

public class AvailabilityVm {
	
	private Long id;
	private String day;
	private String timeOfDay;
	
	
	public AvailabilityVm() {
	}
	
	public AvailabilityVm(Long id, String day, String timeOfDay) {
		super();
		this.id = id;
		this.day = day;
		this.timeOfDay = timeOfDay;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	@Override
	public String toString() {
		return "AvailabilityVm [id=" + id + ", day=" + day + ", timeOfDay=" + timeOfDay + "]";
	}
	

}
