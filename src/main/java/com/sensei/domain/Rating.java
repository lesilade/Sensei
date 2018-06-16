package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Rating.
 */
@Entity
@Table(name = "rating")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "score", nullable = false)
    private Integer score;

    @NotNull
    @Column(name = "date_rated", nullable = false)
    private LocalDate dateRated;

    @OneToOne
    @JoinColumn(unique = true)
    private CoachingSession coachingSession;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public Rating score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDate getDateRated() {
        return dateRated;
    }

    public Rating dateRated(LocalDate dateRated) {
        this.dateRated = dateRated;
        return this;
    }

    public void setDateRated(LocalDate dateRated) {
        this.dateRated = dateRated;
    }

    public CoachingSession getCoachingSession() {
        return coachingSession;
    }

    public Rating coachingSession(CoachingSession coachingSession) {
        this.coachingSession = coachingSession;
        return this;
    }

    public void setCoachingSession(CoachingSession coachingSession) {
        this.coachingSession = coachingSession;
    }

    public User getUser() {
        return user;
    }

    public Rating user(User user) {
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
        Rating rating = (Rating) o;
        if (rating.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rating.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rating{" +
            "id=" + getId() +
            ", score='" + getScore() + "'" +
            ", dateRated='" + getDateRated() + "'" +
            "}";
    }
}
