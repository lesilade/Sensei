package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Messaging.
 */
@Entity
@Table(name = "messaging")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Messaging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chatid")
    private Integer chatid;

    @Size(max = 500)
    @Column(name = "message", length = 500)
    private String message;

    @NotNull
    @Column(name = "sentdate", nullable = false)
    private LocalDate sentdate;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getChatid() {
        return chatid;
    }

    public Messaging chatid(Integer chatid) {
        this.chatid = chatid;
        return this;
    }

    public void setChatid(Integer chatid) {
        this.chatid = chatid;
    }

    public String getMessage() {
        return message;
    }

    public Messaging message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getSentdate() {
        return sentdate;
    }

    public Messaging sentdate(LocalDate sentdate) {
        this.sentdate = sentdate;
        return this;
    }

    public void setSentdate(LocalDate sentdate) {
        this.sentdate = sentdate;
    }

    public User getUser() {
        return user;
    }

    public Messaging user(User user) {
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
        Messaging messaging = (Messaging) o;
        if (messaging.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messaging.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Messaging{" +
            "id=" + getId() +
            ", chatid='" + getChatid() + "'" +
            ", message='" + getMessage() + "'" +
            ", sentdate='" + getSentdate() + "'" +
            "}";
    }
}
