package com.sensei.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Newsfeed.
 */
@Entity
@Table(name = "newsfeed")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Newsfeed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 250)
    @Column(name = "title", length = 250)
    private String title;

    @Size(max = 250)
    @Column(name = "image_url", length = 250)
    private String imageUrl;

    @Size(max = 250)
    @Column(name = "content", length = 250)
    private String content;

    @NotNull
    @Column(name = "date_posted", nullable = false)
    private LocalDateTime datePosted;

    @OneToMany(mappedBy = "newsfeed")
    @JsonIgnore
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

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

    public Newsfeed title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Newsfeed imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public Newsfeed content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public Newsfeed datePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
        return this;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Newsfeed comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Newsfeed addComment(Comment comment) {
        this.comments.add(comment);
        comment.setNewsfeed(this);
        return this;
    }

    public Newsfeed removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setNewsfeed(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public Newsfeed user(User user) {
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
        Newsfeed newsfeed = (Newsfeed) o;
        if (newsfeed.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), newsfeed.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Newsfeed{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", content='" + getContent() + "'" +
            ", datePosted='" + getDatePosted() + "'" +
            "}";
    }
}
