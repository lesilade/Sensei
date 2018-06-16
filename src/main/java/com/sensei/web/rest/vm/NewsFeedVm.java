package com.sensei.web.rest.vm;

import java.util.List;

import com.sensei.domain.Comment;
import com.sensei.domain.Like;
import com.sensei.domain.User;

public class NewsFeedVm {

	private Long id;
	private String imageUrl;
	private String content;
	private String date;
	private User user;
	private List<CommentVm> comment;
	private LikeVm likes;
	
	public NewsFeedVm() {
	}

	public NewsFeedVm(Long id, String imageUrl, String content, String date, User user, List<CommentVm> comment,
			LikeVm likes) {
		super();
		this.id = id;
		this.imageUrl = imageUrl;
		this.content = content;
		this.date = date;
		this.user = user;
		this.comment = comment;
		this.likes = likes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CommentVm> getComment() {
		return comment;
	}

	public void setComment(List<CommentVm> comment) {
		this.comment = comment;
	}

	public LikeVm getLikes() {
		return likes;
	}

	public void setLikes(LikeVm likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "NewsFeedVm [id=" + id + ", imageUrl=" + imageUrl + ", content=" + content + ", date=" + date
				+ ", user=" + user + ", comment=" + comment + ", likes=" + likes + "]";
	}
}
