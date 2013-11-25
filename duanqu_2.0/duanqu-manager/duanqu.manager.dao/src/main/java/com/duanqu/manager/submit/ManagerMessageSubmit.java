package com.duanqu.manager.submit;

import java.io.Serializable;

public class ManagerMessageSubmit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6670354798667946673L;
	
	private String uid;
	private int id;
	private String uidList;
	private String messageText;
	private int fslx;//0 发私信  1回复私信 2 回复反馈信息
	private long messageId;//回复私信的id
	private int type;//1单发入口，2群发入口
	private int qflx;//1给所有人群发，2给选中人群发
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUidList() {
		return uidList;
	}
	public void setUidList(String uidList) {
		this.uidList = uidList;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public int getFslx() {
		return fslx;
	}
	public void setFslx(int fslx) {
		this.fslx = fslx;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getQflx() {
		return qflx;
	}
	public void setQflx(int qflx) {
		this.qflx = qflx;
	}
}
