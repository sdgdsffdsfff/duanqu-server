package com.duanqu.manager.submit;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class ManagerMjUserSubmit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1081192608583395054L;
	
	private String nickName;
	private MultipartFile avatarUrl;//头像
	private MultipartFile backgroundUrl;//背景
	private String signature;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public MultipartFile getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(MultipartFile avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public MultipartFile getBackgroundUrl() {
		return backgroundUrl;
	}
	public void setBackgroundUrl(MultipartFile backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
}
