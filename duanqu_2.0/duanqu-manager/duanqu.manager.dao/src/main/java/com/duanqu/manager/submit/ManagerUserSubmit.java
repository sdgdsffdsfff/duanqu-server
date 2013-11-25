package com.duanqu.manager.submit;

import java.io.Serializable;
import java.util.Date;

import com.duanqu.common.DateUtil;

public class ManagerUserSubmit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3374465869820299864L;
	
	private long uid;
	private String banText;//禁言原因
	private long banEndtime;//禁言结束时间
	private String banEndtimeStr;
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getBanText() {
		return banText;
	}
	public void setBanText(String banText) {
		this.banText = banText;
	}
	public long getBanEndtime() {
		return banEndtime;
	}
	public void setBanEndtime(long banEndtime) {
		this.banEndtime = banEndtime;
	}
	public long toLong(String date) throws Exception{
		Date timeDate=DateUtil.parse(date, "yyy-MM-dd hh:mm:ss");
		return timeDate.getTime();
	}
	public String getBanEndtimeStr() {
		return banEndtimeStr;
	}
	public void setBanEndtimeStr(String banEndtimeStr) {
		this.banEndtimeStr = banEndtimeStr;
	}
	
}
