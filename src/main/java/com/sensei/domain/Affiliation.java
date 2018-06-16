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
 * A Affiliation.
 */
@Entity
@Table(name = "affiliation")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Affiliation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "jhi_organization", length = 200, nullable = false)
    private String organization;

    @NotNull
    @Size(max = 200)
    @Column(name = "jhi_role", length = 200, nullable = false)
    private String role;

    @NotNull
    @Size(max = 200)
    @Column(name = "focusarea", length = 200, nullable = false)
    private String focusarea;

    @NotNull
    @Column(name = "startdate", nullable = false)
    private LocalDateTime startdate;

    @NotNull
    @Column(name = "enddate", nullable = false)
    private LocalDateTime enddate;

    @Column(name = "iscurrent")
    private Boolean iscurrent;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public Affiliation organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRole() {
        return role;
    }

    public Affiliation role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFocusarea() {
        return focusarea;
    }

    public Affiliation focusarea(String focusarea) {
        this.focusarea = focusarea;
        return this;
    }

    public void setFocusarea(String focusarea) {
        this.focusarea = focusarea;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public Affiliation startdate(LocalDateTime startdate) {
        this.startdate = startdate;
        return this;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public Affiliation enddate(LocalDateTime enddate) {
        this.enddate = enddate;
        return this;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public Boolean isIscurrent() {
        return iscurrent;
    }

    public Affiliation iscurrent(Boolean iscurrent) {
        this.iscurrent = iscurrent;
        return this;
    }

    public void setIscurrent(Boolean iscurrent) {
        this.iscurrent = iscurrent;
    }

    public User getUser() {
        return user;
    }

    public Affiliation user(User user) {
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
        Affiliation affiliation = (Affiliation) o;
        if (affiliation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), affiliation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Affiliation{" +
            "id=" + getId() +
            ", organization='" + getOrganization() + "'" +
            ", role='" + getRole() + "'" +
            ", focusarea='" + getFocusarea() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", iscurrent='" + isIscurrent() + "'" +
            "}";
    }
}
