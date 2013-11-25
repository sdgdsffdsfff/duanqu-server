package com.duanqu.manager.submit;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.DateUtil;

public class ManagerContentSubmit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3163477204143865781L;
	
	private String description;// 描述
	private MultipartFile video;// 文件地址
	private MultipartFile thumbnails;// 缩略图
	private int isShow;//投放标志
	private int isDqj;//是否短趣君
	private String uploadTime;
	private long uploadTimeL;
	private int top;//是否置顶
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getVideo() {
		return video;
	}
	public void setVideo(MultipartFile video) {
		this.video = video;
	}
	public MultipartFile getThumbnails() {
		return thumbnails;
	}
	public void setThumbnails(MultipartFile thumbnails) {
		this.thumbnails = thumbnails;
	}
	public int getIsShow() {
		return isShow;
	}
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
	public int getIsDqj() {
		return isDqj;
	}
	public void setIsDqj(int isDqj) {
		this.isDqj = isDqj;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public long getUploadTimeL() {
		return uploadTimeL;
	}
	public void setUploadTimeL(long uploadTimeL) {
		this.uploadTimeL = uploadTimeL;
	}

	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public long toLong(String uploadTime) throws ParseException{
		Date date=DateUtil.parse(uploadTime, "yyyy-MM-dd HH:mm:ss");
		return date.getTime();
	}
}
