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
 * A Education.
 */
@Entity
@Table(name = "education")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "school", length = 250, nullable = false)
    private String school;

    @NotNull
    @Size(max = 250)
    @Column(name = "degree", length = 250, nullable = false)
    private String degree;

    @NotNull
    @Size(max = 250)
    @Column(name = "majorareaofstudy", length = 250, nullable = false)
    private String majorareaofstudy;

    @Size(max = 250)
    @Column(name = "minorareaofstudy", length = 250)
    private String minorareaofstudy;

    @NotNull
    @Column(name = "startdate", nullable = false)
    private LocalDateTime startdate;

    @NotNull
    @Column(name = "enddate", nullable = false)
    private LocalDateTime enddate;

    //@NotNull
   // @Size(max = 250)
   // @Column(name = "content", length = 250, nullable = false)
   // private String content;

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

    public String getSchool() {
        return school;
    }

    public Education school(String school) {
        this.school = school;
        return this;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDegree() {
        return degree;
    }

    public Education degree(String degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajorareaofstudy() {
        return majorareaofstudy;
    }

    public Education majorareaofstudy(String majorareaofstudy) {
        this.majorareaofstudy = majorareaofstudy;
        return this;
    }

    public void setMajorareaofstudy(String majorareaofstudy) {
        this.majorareaofstudy = majorareaofstudy;
    }

    public String getMinorareaofstudy() {
        return minorareaofstudy;
    }

    public Education minorareaofstudy(String minorareaofstudy) {
        this.minorareaofstudy = minorareaofstudy;
        return this;
    }

    public void setMinorareaofstudy(String minorareaofstudy) {
        this.minorareaofstudy = minorareaofstudy;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public Education startdate(LocalDateTime startdate) {
        this.startdate = startdate;
        return this;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public Education enddate(LocalDateTime enddate) {
        this.enddate = enddate;
        return this;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

//    public String getContent() {
//        return content;
//    }

//    public Education content(String content) {
//        this.content = content;
//        return this;
//    }

//    public void setContent(String content) {
//        this.content = content;
//    }

    public Boolean isIscurrent() {
        return iscurrent;
    }

    public Education iscurrent(Boolean iscurrent) {
        this.iscurrent = iscurrent;
        return this;
    }

    public void setIscurrent(Boolean iscurrent) {
        this.iscurrent = iscurrent;
    }

    public User getUser() {
        return user;
    }

    public Education user(User user) {
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
        Education education = (Education) o;
        if (education.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), education.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Education{" +
            "id=" + getId() +
            ", school='" + getSchool() + "'" +
            ", degree='" + getDegree() + "'" +
            ", majorareaofstudy='" + getMajorareaofstudy() + "'" +
            ", minorareaofstudy='" + getMinorareaofstudy() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", iscurrent='" + isIscurrent() + "'" +
            "}";
    }
}
