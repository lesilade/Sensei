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
 * A Comment.
 */
@Entity
@Table(name = "comment")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "content", length = 250, nullable = false)
    private String content;

    @Column(name = "reply_to_comment_id")
    private Integer replyToCommentId;

    @NotNull
    @Column(name = "date_posted", nullable = false)
    private LocalDateTime datePosted;

    @ManyToOne
    private Newsfeed newsfeed;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Comment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReplyToCommentId() {
        return replyToCommentId;
    }

    public Comment replyToCommentId(Integer replyToCommentId) {
        this.replyToCommentId = replyToCommentId;
        return this;
    }

    public void setReplyToCommentId(Integer replyToCommentId) {
        this.replyToCommentId = replyToCommentId;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public Comment datePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
        return this;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public Newsfeed getNewsfeed() {
        return newsfeed;
    }

    public Comment newsfeed(Newsfeed newsfeed) {
        this.newsfeed = newsfeed;
        return this;
    }

    public void setNewsfeed(Newsfeed newsfeed) {
        this.newsfeed = newsfeed;
    }

    public User getUser() {
        return user;
    }

    public Comment user(User user) {
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
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", replyToCommentId='" + getReplyToCommentId() + "'" +
            ", datePosted='" + getDatePosted() + "'" +
            "}";
    }
}
