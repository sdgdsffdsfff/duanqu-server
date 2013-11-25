package com.duanqu;

import java.io.Serializable;
import java.util.List;

public class ContentForm implements Serializable{
	
	private static final long serialVersionUID = 3023381366096139615L;
	private long id;
	private String title;
	private String videoUrl;
	private String thumbnail;
	UserForm user;//发布者
	List<UserForm> likeUsers;// 喜欢用户
	
	List<CommentForm> comments;//评论列表

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public UserForm getUser() {
		return user;
	}

	public void setUser(UserForm user) {
		this.user = user;
	}

	public List<UserForm> getLikeUsers() {
		return likeUsers;
	}

	public void setLikeUsers(List<UserForm> likeUsers) {
		this.likeUsers = likeUsers;
	}

	public List<CommentForm> getComments() {
		return comments;
	}

	public void setComments(List<CommentForm> comments) {
		this.comments = comments;
	}

}
