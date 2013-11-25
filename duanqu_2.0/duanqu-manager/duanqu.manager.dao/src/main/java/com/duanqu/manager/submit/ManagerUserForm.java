package com.duanqu.manager.submit;

import java.io.Serializable;
import java.sql.Date;

import com.duanqu.common.DateUtil;

public class ManagerUserForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3348794968636817520L;
	
	private long uid;
	private String avatarUrl;
	private String nickName;
	private String bdfs;//绑定方式
	private long createTime;//注册时间
	private long lastLoginTime;//最后一次登录时间
	private int followNum;//关注数
	private int fansNum;//真粉丝数
	private int fansFalseNum;//假粉丝数
	private int friendNum;//好友数
	private int nrs;//内容数
	private int fxs;//分享总数
	private int xpls;//新评论数
	private String mobile;
	private int status;
	private int isAuthentication;//是否认证用户
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getBdfs() {
		return bdfs;
	}
	public void setBdfs(String bdfs) {
		this.bdfs = bdfs;
	}
	public String getCreateTime() {
		return DateUtil.format(new Date(createTime),"yyyy-MM-dd HH:mm:ss");
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getLastLoginTime() {
		return DateUtil.format(new Date(lastLoginTime),"yyyy-MM-dd HH:mm:ss");
	}
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public int getFollowNum() {
		return followNum;
	}
	public void setFollowNum(int followNum) {
		this.followNum = followNum;
	}
	public int getFansNum() {
		return fansNum;
	}
	public void setFansNum(int fansNum) {
		this.fansNum = fansNum;
	}
	public int getFansFalseNum() {
		return fansFalseNum;
	}
	public void setFansFalseNum(int fansFalseNum) {
		this.fansFalseNum = fansFalseNum;
	}
	public int getFriendNum() {
		return friendNum;
	}
	public void setFriendNum(int friendNum) {
		this.friendNum = friendNum;
	}
	public int getNrs() {
		return nrs;
	}
	public void setNrs(int nrs) {
		this.nrs = nrs;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getXpls() {
		return xpls;
	}
	public void setXpls(int xpls) {
		this.xpls = xpls;
	}
	public int getFxs() {
		return fxs;
	}
	public void setFxs(int fxs) {
		this.fxs = fxs;
	}
	public int getIsAuthentication() {
		return isAuthentication;
	}
	public void setIsAuthentication(int isAuthentication) {
		this.isAuthentication = isAuthentication;
	}
	
}
