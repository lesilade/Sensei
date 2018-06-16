package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CoachAvailability.
 */
@Entity
@Table(name = "coach_availability")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CoachAvailability implements Serializable {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public CoachAvailability day(String day) {
        this.day = day;
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getTimeofday() {
        return timeofday;
    }

    public CoachAvailability timeofday(Integer timeofday) {
        this.timeofday = timeofday;
        return this;
    }

    public void setTimeofday(Integer timeofday) {
        this.timeofday = timeofday;
    }

    public User getUser() {
        return user;
    }

    public CoachAvailability user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoachAvailability coachAvailability = (CoachAvailability) o;
        if (coachAvailability.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coachAvailability.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoachAvailability{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", timeofday='" + getTimeofday() + "'" +
            "}";
    }
}
