package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TraineeAvailability.
 */
@Entity
@Table(name = "trainee_availability")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraineeAvailability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "day", nullable = false)
    private String day;

    @Column(name = "timeofday")
    private Integer timeofday;

    @ManyToOne
    private User user;

    @ManyToOne
    private CaochingRequest caochingRequest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public TraineeAvailability day(String day) {
        this.day = day;
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getTimeofday() {
        return timeofday;
    }

    public TraineeAvailability timeofday(Integer timeofday) {
        this.timeofday = timeofday;
        return this;
    }

    public void setTimeofday(Integer timeofday) {
        this.timeofday = timeofday;
    }

    public User getUser() {
        return user;
    }

    public TraineeAvailability user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CaochingRequest getCaochingRequest() {
        return caochingRequest;
    }

    public TraineeAvailability caochingRequest(CaochingRequest caochingRequest) {
        this.caochingRequest = caochingRequest;
        return this;
    }

    public void setCaochingRequest(CaochingRequest caochingRequest) {
        this.caochingRequest = caochingRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraineeAvailability traineeAvailability = (TraineeAvailability) o;
        if (traineeAvailability.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), traineeAvailability.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TraineeAvailability{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", timeofday='" + getTimeofday() + "'" +
            "}";
    }
}
