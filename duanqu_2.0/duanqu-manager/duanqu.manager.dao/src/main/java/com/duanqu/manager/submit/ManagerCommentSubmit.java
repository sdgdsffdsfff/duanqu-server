package com.duanqu.manager.submit;

import java.io.Serializable;

import com.duanqu.common.model.UserModel;
import com.duanqu.common.vo.SimpleUserForm;

public class ManagerCommentSubmit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5527991747497345345L;
	
	private long id;//评论ID	
	private String commentText;// 评论内容
	private long createTime;//发表时间
    private long uid;//评论发布者
    private String nickName;//发布者昵称
    private String avatarUrl;//发布者头像
    private long cid;//评论内容id
    private String videoUrlHd;//视频地址
    private String thumbnailsUrl;//首帧图片
    private long rootId;//
    private long prientId;
    private ManagerCommentSubmit managerCommentSubmit;
    private UserModel replyUser;
    private long replyUid;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
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
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public String getVideoUrlHd() {
		return videoUrlHd;
	}
	public void setVideoUrlHd(String videoUrlHd) {
		this.videoUrlHd = videoUrlHd;
	}
	public String getThumbnailsUrl() {
		return thumbnailsUrl;
	}
	public void setThumbnailsUrl(String thumbnailsUrl) {
		this.thumbnailsUrl = thumbnailsUrl;
	}
	public long getRootId() {
		return rootId;
	}
	public void setRootId(long rootId) {
		this.rootId = rootId;
	}
	public ManagerCommentSubmit getManagerCommentSubmit() {
		return managerCommentSubmit;
	}
	public void setManagerCommentSubmit(ManagerCommentSubmit managerCommentSubmit) {
		this.managerCommentSubmit = managerCommentSubmit;
	}
	public UserModel getReplyUser() {
		return replyUser;
	}
	public void setReplyUser(UserModel replyUser) {
		this.replyUser = replyUser;
	}
	public long getReplyUid() {
		return replyUid;
	}
	public void setReplyUid(long replyUid) {
		this.replyUid = replyUid;
	}
	public long getPrientId() {
		return prientId;
	}
	public void setPrientId(long prientId) {
		this.prientId = prientId;
	}
	
	
	
}
