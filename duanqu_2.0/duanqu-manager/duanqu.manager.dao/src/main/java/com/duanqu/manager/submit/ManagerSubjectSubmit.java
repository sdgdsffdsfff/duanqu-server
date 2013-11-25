package com.duanqu.manager.submit;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.submit.Pager;

public class ManagerSubjectSubmit extends Pager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int sid;
	private String title;
	private String description;
	private MultipartFile videoFile;
	private MultipartFile imgFile;
	private MultipartFile explainFile;
	private String innerParam;
	private MultipartFile thumbnailsFile;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getVideoFile() {
		return videoFile;
	}
	public void setVideoFile(MultipartFile videoFile) {
		this.videoFile = videoFile;
	}
	public MultipartFile getImgFile() {
		return imgFile;
	}
	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}
	public MultipartFile getThumbnailsFile() {
		return thumbnailsFile;
	}
	public void setThumbnailsFile(MultipartFile thumbnailsFile) {
		this.thumbnailsFile = thumbnailsFile;
	}
	public MultipartFile getExplainFile() {
		return explainFile;
	}
	public void setExplainFile(MultipartFile explainFile) {
		this.explainFile = explainFile;
	}
	public String getInnerParam() {
		return innerParam;
	}
	public void setInnerParam(String innerParam) {
		this.innerParam = innerParam;
	}
}
