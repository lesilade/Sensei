package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Coachingconnections.
 */
@Entity
@Table(name = "coachingconnections")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Coachingconnections implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User usertwo;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsertwo() {
        return usertwo;
    }

    public Coachingconnections usertwo(User usertwo) {
        this.usertwo = usertwo;
        return this;
    }

    public void setUsertwo(User usertwo) {
        this.usertwo = usertwo;
    }

    public Integer getStatus() {
        return status;
    }

    public Coachingconnections status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public Coachingconnections connection(User user) {
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
        Coachingconnections coachingconnections = (Coachingconnections) o;
        if (coachingconnections.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coachingconnections.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coachingconnections{" +
            "id=" + getId() +
            ", usertwo ='" + getUsertwo() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
