package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Connections.
 */
@Entity
@Table(name = "connections")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Connections implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "follower_id", nullable = false)
    private Long followerId;

    @NotNull
    @Column(name = "followed_id", nullable = false)
    private Long followedId;

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

    public Long getFollowerId() {
        return followerId;
    }

    public Connections followerId(Long followerId) {
        this.followerId = followerId;
        return this;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowedId() {
        return followedId;
    }

    public Connections followedId(Long followedId) {
        this.followedId = followedId;
        return this;
    }

    public void setFollowedId(Long followedId) {
        this.followedId = followedId;
    }

    public Integer getStatus() {
        return status;
    }

    public Connections status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public Connections user(User user) {
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
        Connections connections = (Connections) o;
        if (connections.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), connections.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Connections{" +
            "id=" + getId() +
            ", followerId='" + getFollowerId() + "'" +
            ", followedId='" + getFollowedId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
