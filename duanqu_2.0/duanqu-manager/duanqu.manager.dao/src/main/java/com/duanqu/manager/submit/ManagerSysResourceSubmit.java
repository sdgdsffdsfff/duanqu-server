package com.duanqu.manager.submit;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.submit.Pager;

public class ManagerSysResourceSubmit extends Pager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private MultipartFile resourceFile;
	private MultipartFile resourceIconFile;
	private MultipartFile resourceMusicFile;
	private String description;
	private int type;
	public MultipartFile getResourceFile() {
		return resourceFile;
	}
	public void setResourceFile(MultipartFile resourceFile) {
		this.resourceFile = resourceFile;
	}
	public MultipartFile getResourceIconFile() {
		return resourceIconFile;
	}
	public void setResourceIconFile(MultipartFile resourceIconFile) {
		this.resourceIconFile = resourceIconFile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public MultipartFile getResourceMusicFile() {
		return resourceMusicFile;
	}
	public void setResourceMusicFile(MultipartFile resourceMusicFile) {
		this.resourceMusicFile = resourceMusicFile;
	}
	
	

}
