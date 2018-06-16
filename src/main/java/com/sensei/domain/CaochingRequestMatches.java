package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CaochingRequestMatches.
 */
@Entity
@Table(name = "caoching_request_matches")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CaochingRequestMatches implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "request_name", length = 250, nullable = false)
    private String requestName;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne
    private CaochingRequest caochingRequest;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestName() {
        return requestName;
    }

    public CaochingRequestMatches requestName(String requestName) {
        this.requestName = requestName;
        return this;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public Integer getStatus() {
        return status;
    }

    public CaochingRequestMatches status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CaochingRequest getCaochingRequest() {
        return caochingRequest;
    }

    public CaochingRequestMatches caochingRequest(CaochingRequest caochingRequest) {
        this.caochingRequest = caochingRequest;
        return this;
    }

    public void setCaochingRequest(CaochingRequest caochingRequest) {
        this.caochingRequest = caochingRequest;
    }

    public User getUser() {
        return user;
    }

    public CaochingRequestMatches user(User user) {
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
        CaochingRequestMatches caochingRequestMatches = (CaochingRequestMatches) o;
        if (caochingRequestMatches.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caochingRequestMatches.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaochingRequestMatches{" +
            "id=" + getId() +
            ", requestName='" + getRequestName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
