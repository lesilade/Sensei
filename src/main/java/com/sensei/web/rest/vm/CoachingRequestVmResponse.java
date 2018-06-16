package com.sensei.web.rest.vm;

import java.util.ArrayList;
import java.util.List;

import com.sensei.domain.User;

public class CoachingRequestVmResponse {

    private Long id;
    private Boolean closeBy;
    private String description;
    private Integer duration;
    private Boolean inNetwork;
    private String industry;
    private String subtopic;
    private String topic;
	
    private List<TraineeAvailabilityVM> availability = new ArrayList<TraineeAvailabilityVM>();
    
    private User user;

    
    
	public CoachingRequestVmResponse() {
	}


	public CoachingRequestVmResponse(Long id, Boolean closeBy, String description, Integer duration, Boolean inNetwork,
			String industry, String subtopic, String topic, List<TraineeAvailabilityVM> availability, User user) {
		super();
		this.id = id;
		this.closeBy = closeBy;
		this.description = description;
		this.duration = duration;
		this.inNetwork = inNetwork;
		this.industry = industry;
		this.subtopic = subtopic;
		this.topic = topic;
		this.availability = availability;
		this.user = user;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Boolean getCloseBy() {
		return closeBy;
	}


	public void setCloseBy(Boolean closeBy) {
		this.closeBy = closeBy;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getDuration() {
		return duration;
	}


	public void setDuration(Integer duration) {
		this.duration = duration;
	}


	public Boolean getInNetwork() {
		return inNetwork;
	}


	public void setInNetwork(Boolean inNetwork) {
		this.inNetwork = inNetwork;
	}


	public String getIndustry() {
		return industry;
	}


	public void setIndustry(String industry) {
		this.industry = industry;
	}


	public String getSubtopic() {
		return subtopic;
	}


	public void setSubtopic(String subtopic) {
		this.subtopic = subtopic;
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}


	public List<TraineeAvailabilityVM> getAvailability() {
		return availability;
	}


	public void setAvailability(List<TraineeAvailabilityVM> availability) {
		this.availability = availability;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "CoachingRequestVmResponse [id=" + id + ", closeBy=" + closeBy + ", description=" + description
				+ ", duration=" + duration + ", inNetwork=" + inNetwork + ", industry=" + industry + ", subtopic="
				+ subtopic + ", topic=" + topic + ", availability=" + availability + ", user=" + user + "]";
	}
    
     
}
