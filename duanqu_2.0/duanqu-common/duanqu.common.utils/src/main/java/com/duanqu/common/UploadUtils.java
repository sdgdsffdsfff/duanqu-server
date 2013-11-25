package com.duanqu.common;

import java.io.File;
import java.io.IOException;

public class UploadUtils {

	private static UpYun uploadImageUpYun = null;
	private static UpYun uploadVideoUpYun = null;
	private static UpYun uploadAvatarUpYun = null;

	static {
		uploadImageUpYun = new UpYun(DuanquConfig.getImageBucketName(),
				DuanquConfig.getUpyunUserName(), DuanquConfig
						.getUpyunPassword());
		uploadVideoUpYun = new UpYun(DuanquConfig.getVideoBucketName(),
				DuanquConfig.getUpyunUserName(), DuanquConfig
						.getUpyunPassword());
		uploadAvatarUpYun = new UpYun(DuanquConfig.getAvatarBucketName(),
				DuanquConfig.getUpyunUserName(), DuanquConfig
						.getUpyunPassword());
	}
	
	public static boolean uploadVideo(String filePath,File video) throws IOException{
		//uploadVideoUpYun.setDebug(true);
		return uploadVideoUpYun.writeFile(filePath, video,true);
	}
	
	public static boolean uploadImage(String filePath,File image) throws IOException{
		//uploadImageUpYun.setDebug(true);
		return uploadImageUpYun.writeFile(filePath, image,true);
	}
	
	public static boolean uploadAvatar(String filePath,byte[] avatar) throws IOException{
		return uploadAvatarUpYun.writeFile(filePath, avatar,true);
	}

	public static void main(String[] args) {
		try {
			UploadUtils.uploadImage("tag/test.jpg", new File("/home/wanghaihua/duanqu2.0/resources/thumbnail/20130516/11368710523336.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
