package com.duanqu;

import java.io.Serializable;
import java.util.Date;

public class CommentForm implements Serializable {
	
	private static final long serialVersionUID = -4842853392558105217L;
	private long id;//评论ID	
	private String text;// 评论内容
	private Date time;//发表时间
	
	private UserForm user;//发表者

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public UserForm getUser() {
		return user;
	}

	public void setUser(UserForm user) {
		this.user = user;
	}
	
	
	

}
