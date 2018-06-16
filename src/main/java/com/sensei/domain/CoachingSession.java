package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A CoachingSession.
 */
@Entity
@Table(name = "coaching_session")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CoachingSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Size(max = 200)
    @Column(name = "user_role", length = 200, nullable = false)
    private String role;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    private User user;
    
    @ManyToOne
    private CaochingRequest caochingRequest;
    
    @NotNull
    private boolean isActive;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public CoachingSession title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public CoachingSession status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public CoachingSession role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public CoachingSession startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public CoachingSession endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public CoachingSession user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    

    public CaochingRequest getCaochingRequest() {
		return caochingRequest;
	}

	public void setCaochingRequest(CaochingRequest caochingRequest) {
		this.caochingRequest = caochingRequest;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoachingSession coachingSession = (CoachingSession) o;
        if (coachingSession.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coachingSession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "CoachingSession [id=" + id + ", title=" + title + ", status=" + status + ", role=" + role
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", user=" + user + ", caochingRequest="
				+ caochingRequest + ", isActive=" + isActive + "]";
	}
    
}
