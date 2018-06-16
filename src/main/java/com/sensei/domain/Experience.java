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
 * A Experience.
 */
@Entity
@Table(name = "experience")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Experience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @NotNull
    @Size(max = 200)
    @Column(name = "organization", length = 200, nullable = false)
    private String organization;

    @NotNull
    @Size(max = 200)
    @Column(name = "city", length = 200, nullable = false)
    private String city;

    @NotNull
    @Size(max = 200)
    @Column(name = "state", length = 200, nullable = false)
    private String state;

    @NotNull
    @Column(name = "startdate", nullable = false)
    private LocalDateTime startdate;

    @Column(name = "enddate")
    private LocalDateTime enddate;

    @NotNull
    @Size(max = 250)
    @Column(name = "content", length = 250, nullable = false)
    private String content;

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

    public String getTitle() {
        return title;
    }

    public Experience title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganization() {
        return organization;
    }

    public Experience organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCity() {
        return city;
    }

    public Experience city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Experience state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public Experience startdate(LocalDateTime startdate) {
        this.startdate = startdate;
        return this;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public Experience enddate(LocalDateTime enddate) {
        this.enddate = enddate;
        return this;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public String getContent() {
        return content;
    }

    public Experience content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isIscurrent() {
        return iscurrent;
    }

    public Experience iscurrent(Boolean iscurrent) {
        this.iscurrent = iscurrent;
        return this;
    }

    public void setIscurrent(Boolean iscurrent) {
        this.iscurrent = iscurrent;
    }

    public User getUser() {
        return user;
    }

    public Experience user(User user) {
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
        Experience experience = (Experience) o;
        if (experience.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experience.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Experience{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", organization='" + getOrganization() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", content='" + getContent() + "'" +
            ", iscurrent='" + isIscurrent() + "'" +
            "}";
    }
}
