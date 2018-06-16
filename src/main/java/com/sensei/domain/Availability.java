package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Availability.
 */
@Entity
@Table(name = "availability")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Availability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "day", nullable = false)
    private String day;

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

    public Availability day(String day) {
        this.day = day;
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public CaochingRequest getCaochingRequest() {
        return caochingRequest;
    }

    public Availability caochingRequest(CaochingRequest caochingRequest) {
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
        Availability availability = (Availability) o;
        if (availability.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), availability.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Availability{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            "}";
    }
}
