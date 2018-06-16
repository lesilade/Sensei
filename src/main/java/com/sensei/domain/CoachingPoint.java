package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CoachingPoint.
 */
@Entity
@Table(name = "coaching_point")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CoachingPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "point", nullable = false)
    private Integer point;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoint() {
        return point;
    }

    public CoachingPoint point(Integer point) {
        this.point = point;
        return this;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public User getUser() {
        return user;
    }

    public CoachingPoint user(User user) {
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
        CoachingPoint coachingPoint = (CoachingPoint) o;
        if (coachingPoint.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coachingPoint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoachingPoint{" +
            "id=" + getId() +
            ", point='" + getPoint() + "'" +
            "}";
    }
}
