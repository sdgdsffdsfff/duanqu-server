package com.duanqu;

import java.io.Serializable;

public class UserForm implements Serializable{
	private static final long serialVersionUID = -8229711522654999723L;
	private long uid;//id
	private String nickName;//昵称
	private String avatar;//头像
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
