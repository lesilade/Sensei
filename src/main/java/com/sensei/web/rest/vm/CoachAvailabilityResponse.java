package com.sensei.web.rest.vm;

import java.util.ArrayList;
import java.util.List;

import com.sensei.domain.User;

public class CoachAvailabilityResponse {

	private User user;
	private List<AvailabilityVm> availability = new ArrayList<AvailabilityVm>();

	public CoachAvailabilityResponse() {

	}

	public CoachAvailabilityResponse(User user, List<AvailabilityVm> availability) {
		super();
		this.user = user;
		this.availability = availability;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<AvailabilityVm> getAvailability() {
		return availability;
	}

	public void setAvailability(List<AvailabilityVm> availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return "CoachAvailabilityResponse [user=" + user + ", availability=" + availability + "]";
	}
	
}
