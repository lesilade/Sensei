package com.sensei.web.rest.vm;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;


public class CaochingRequestVM {

	private Long id;
	
	@NotNull
    private Boolean closeBy;
	@NotNull
    private String description;
	@NotNull
    private Integer duration;
	@NotNull
    private Boolean inNetwork;
	@NotNull
    private String industry;
	@NotNull
    private String subtopic;
	@NotNull
    private String topic;
	
	@NotNull
    private List<TraineeAvailabilityVM> availability = new ArrayList<TraineeAvailabilityVM>();
    
    @NotNull
    private String username;
    
    
	public CaochingRequestVM() {
	}


	public CaochingRequestVM(Long id, Boolean closeBy, String description, Integer duration, Boolean inNetwork, String industry,
			String subtopic, String topic, List<TraineeAvailabilityVM> availability, String username) {
		super();
		this.id= id;
		this.closeBy = closeBy;
		this.description = description;
		this.duration = duration;
		this.inNetwork = inNetwork;
		this.industry = industry;
		this.subtopic = subtopic;
		this.topic = topic;
		this.availability = availability;
		this.username = username;
	}

	
	public Long getId() {
		return id;
	}
	
	

	public List<TraineeAvailabilityVM> getAvailability() {
		return availability;
	}


	public void setAvailability(List<TraineeAvailabilityVM> availability) {
		this.availability = availability;
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


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public String toString() {
		return "CaochingRequestVM [id=" + id + ", closeBy=" + closeBy + ", description=" + description + ", duration=" + duration
				+ ", inNetwork=" + inNetwork + ", industry=" + industry + ", subtopic=" + subtopic + ", topic=" + topic
				+ ", availability=" + availability + ", username=" + username + "]";
	}
	

}
