package com.duanqu.manager.submit;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.submit.Pager;


public class ManagerBannerSubmit extends Pager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2732571953590134008L;
	
	private List<Long> numList;
	private List<Long> bidList;
	private int bid;
	private String title;
	private MultipartFile imgUrl;
	private String bannerType;
	private String innerParam;
	public List<Long> getNumList() {
		return numList;
	}
	public void setNumList(List<Long> numList) {
		this.numList = numList;
	}
	public List<Long> getBidList() {
		return bidList;
	}
	public void setBidList(List<Long> bidList) {
		this.bidList = bidList;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public MultipartFile getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(MultipartFile imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getBannerType() {
		return bannerType;
	}
	public void setBannerType(String bannerType) {
		this.bannerType = bannerType;
	}
	public String getInnerParam() {
		return innerParam;
	}
	public void setInnerParam(String innerParam) {
		this.innerParam = innerParam;
	}
	
	
	
	

}
