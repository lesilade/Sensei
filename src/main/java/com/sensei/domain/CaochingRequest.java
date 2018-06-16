package com.sensei.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sensei.web.rest.vm.TraineeAvailabilityVM;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A CaochingRequest.
 */
@Entity
@Table(name = "caoching_request")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CaochingRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "topic", length = 250, nullable = false)
    private String topic;

    @NotNull
    @Size(max = 250)
    @Column(name = "subtopic", length = 250, nullable = false)
    private String subtopic;

    @NotNull
    @Size(max = 100)
    @Column(name = "industry", length = 100, nullable = false)
    private String industry;

    @NotNull
    @Size(max = 250)
    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @NotNull
    @Column(name = "in_network", nullable = false)
    private Boolean inNetwork;

    @NotNull
    @Column(name = "close_by", nullable = false)
    private Boolean closeBy;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;
    
    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @OneToMany(mappedBy = "caochingRequest")
    @JsonIgnore
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TraineeAvailability> traineeAvailabilities = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public CaochingRequest topic(String topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubtopic() {
        return subtopic;
    }

    public CaochingRequest subtopic(String subtopic) {
        this.subtopic = subtopic;
        return this;
    }

    public void setSubtopic(String subtopic) {
        this.subtopic = subtopic;
    }

    public String getIndustry() {
        return industry;
    }

    public CaochingRequest industry(String industry) {
        this.industry = industry;
        return this;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDescription() {
        return description;
    }

    public CaochingRequest description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isInNetwork() {
        return inNetwork;
    }

    public CaochingRequest inNetwork(Boolean inNetwork) {
        this.inNetwork = inNetwork;
        return this;
    }

    public void setInNetwork(Boolean inNetwork) {
        this.inNetwork = inNetwork;
    }

    public Boolean isCloseBy() {
        return closeBy;
    }

    public CaochingRequest closeBy(Boolean closeBy) {
        this.closeBy = closeBy;
        return this;
    }

    public void setCloseBy(Boolean closeBy) {
        this.closeBy = closeBy;
    }

    public Integer getDuration() {
        return duration;
    }

    public CaochingRequest duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<TraineeAvailability> getTraineeAvailabilities() {
        return traineeAvailabilities;
    }

    public CaochingRequest traineeAvailabilities(Set<TraineeAvailability> traineeAvailabilities) {
        this.traineeAvailabilities = traineeAvailabilities;
        return this;
    }

    public CaochingRequest addTraineeAvailability(TraineeAvailability traineeAvailability) {
       
    	this.traineeAvailabilities.add(traineeAvailability);
        traineeAvailability.setCaochingRequest(this);
        return this;
    }

    public CaochingRequest removeTraineeAvailability(TraineeAvailability traineeAvailability) {
        this.traineeAvailabilities.remove(traineeAvailability);
        traineeAvailability.setCaochingRequest(null);
        return this;
    }

    public void setTraineeAvailabilities(Set<TraineeAvailability> traineeAvailabilities) {
        this.traineeAvailabilities = traineeAvailabilities;
    }

    public User getUser() {
        return user;
    }

    public CaochingRequest user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void convertTraineeAvailability(List<TraineeAvailabilityVM> traineeAvailabilityVM)
    {
    	
    	traineeAvailabilityVM.stream().forEach(
    			
    			availability -> {
    				
    		    	TraineeAvailability traineeAvailability = new TraineeAvailability();
    		    	traineeAvailability.setDay(availability.getDay());
    		    	
      			  if(availability.getTimeofday() != null)
      			  {
          			  if(availability.getTimeofday().equalsIgnoreCase("morning"))
          			  {
          				traineeAvailability.setTimeofday(TimeOfDay.MORNING.getTimeCode());
          			  }
          			  else if(availability.getTimeofday().equalsIgnoreCase("afternoon"))
          			  {
          				traineeAvailability.setTimeofday(TimeOfDay.AFTERNOON.getTimeCode());
          			  }
          			  else if(availability.getTimeofday().equalsIgnoreCase("evening"))
          			  {
          				traineeAvailability.setTimeofday(TimeOfDay.EVENING.getTimeCode());
          			  }
      			  }
      			  
    		    	this.traineeAvailabilities.add(traineeAvailability);
    			}
    			
    			);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CaochingRequest caochingRequest = (CaochingRequest) o;
        if (caochingRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caochingRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaochingRequest{" +
            "id=" + getId() +
            ", topic='" + getTopic() + "'" +
            ", subtopic='" + getSubtopic() + "'" +
            ", industry='" + getIndustry() + "'" +
            ", description='" + getDescription() + "'" +
            ", inNetwork='" + isInNetwork() + "'" +
            ", closeBy='" + isCloseBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", duration='" + getDuration() + "'" +
            "}";
    }
}

