package com.duanqu.common;

import java.util.List;
import java.util.Set;

public class DuanquConfig {
	
	//文件访问HOST
	private static String host;
	//wap访问HOST
	private static String wapHost;
	//缩略图，视频，头像 根文件夹
	private static String rootFolder;
	//缩略图子文件夹
	private static String thumbnailFolder;
	//视频子文件夹
	private static String videoFolder;
	//头像子文件夹
	private static String avatarFolder;
	//缩略图访问路径
	private static String thumbnailPath;
	//视频访问路径
	private static String videoPath;
	//头像访问路径
	private static String avatarPath;
	//默认头像
	private static String defaultAvatar;
	//热门人物阀值 一般默认5000
	private static int famousThreshold = 50000;//
	//又拍云图片空间
	private static String imageBucketName = "";
	//又拍云视频空间
	private static String videoBucketName = "";
	//又拍云头像空间
	private static String avatarBucketName = "";
	//又拍云空间用户名
	private static String upyunUserName = "";
	//又拍云空间密码
	private static String upyunPassword = "";
	//又拍云图片访问地址
	private static String upyunImageDomain = "";
	//又拍云视频访问地址
	private static String upyunVideoDomain = "";
	//又拍云头像访问地址
	private static String upyunAvatarDomain = "";
	//有描述的@消息
	private static String atMessage = "";
	//没有描述的@消息
	private static String atMessage2 = "";
	//有描述的分享信息
	private static String shareMessage = "";
	//没有描述的分享信息
	private static String shareMessage2 = "";
	//分享来源
	private static String comeFrom = "";
	//阿里云BannerBucket
	private static String aliyunBannerBucketName = "online-duanqu-banner";
	//阿里云头像Bucket
	private static String aliyunAvatarBucketName = "online-duanqu-avatar";
	//阿里云缩略图Bucket
	private static String aliyunThumbnailBucketName = "online-duanqu-thumbnail";
	//阿里云视频Bucket
	private static String aliyunVideoBucketName = "online-duanqu-video";
	//阿里云高清视频Bucket
	private static String aliyunHDVideoBucketName = "online-duanqu-video-hd";
	//阿里云运营图片Bucket
	private static String aliyunSystemImagesBucketName = "online-system-images";
	//阿里云banner域名
	private static String aliyunBannerDomain = "http://online-duanqu-banner.oss.aliyuncs.com/";
	//阿里云头像域名
	private static String aliyunAvatarDomain = "http://online-duanqu-avatar.oss.aliyuncs.com/";
	//阿里云缩略图域名
	private static String aliyunThumbnailDomain = "http://online-duanqu-thumbnail.oss.aliyuncs.com/";
	//阿里云视频域名
	private static String aliyunVideoDomain = "http://online-duanqu-video.oss.aliyuncs.com/";
	//阿里云缩略图域名
	private static String aliyunHDVideoDomain = "http://online-duanqu-video-hd.oss.aliyuncs.com/";
	//阿里云运营图片
	private static String aliyunSystemImagesDomain = "http://online-system-images.oss.aliyuncs.com/";
	//默认组
	private static List<String> groups ;
	
	private static Set<String> imageContentType;
	
	private static String certPath = "";
	
	//转化脚本的存放位置
	private static String convertShellPath = "";
	//转化结果的存放位置
	private static String convertResultPath = "";
	
	public static String getHost() {
		return host;
	}
	public static String getRootFolder() {
		return rootFolder;
	}
	public static String getThumbnailFolder() {
		return thumbnailFolder;
	}
	public static String getVideoFolder() {
		return videoFolder;
	}
	public static String getAvatarFolder() {
		return avatarFolder;
	}
	public static String getThumbnailPath() {
		return thumbnailPath;
	}
	public static String getVideoPath() {
		return videoPath;
	}
	public static String getAvatarPath() {
		return avatarPath;
	}
	public static String getDefaultAvatar() {
		return defaultAvatar;
	}
	public static int getFamousThreshold() {
		return famousThreshold;
	}
	public static String getImageBucketName() {
		return imageBucketName;
	}
	public static String getVideoBucketName() {
		return videoBucketName;
	}
	public static String getUpyunUserName() {
		return upyunUserName;
	}
	public static String getUpyunPassword() {
		return upyunPassword;
	}
	public static String getUpyunImageDomain() {
		return upyunImageDomain;
	}
	public static String getUpyunVideoDomain() {
		return upyunVideoDomain;
	}
	public static void setHost(String host) {
		DuanquConfig.host = host;
	}
	public static void setRootFolder(String rootFolder) {
		DuanquConfig.rootFolder = rootFolder;
	}
	public static void setThumbnailFolder(String thumbnailFolder) {
		DuanquConfig.thumbnailFolder = thumbnailFolder;
	}
	public static void setVideoFolder(String videoFolder) {
		DuanquConfig.videoFolder = videoFolder;
	}
	public static void setAvatarFolder(String avatarFolder) {
		DuanquConfig.avatarFolder = avatarFolder;
	}
	public static void setThumbnailPath(String thumbnailPath) {
		DuanquConfig.thumbnailPath = thumbnailPath;
	}
	public static void setVideoPath(String videoPath) {
		DuanquConfig.videoPath = videoPath;
	}
	public static void setAvatarPath(String avatarPath) {
		DuanquConfig.avatarPath = avatarPath;
	}
	public static void setDefaultAvatar(String defaultAvatar) {
		DuanquConfig.defaultAvatar = defaultAvatar;
	}
	public static void setFamousThreshold(int famousThreshold) {
		DuanquConfig.famousThreshold = famousThreshold;
	}
	public static void setImageBucketName(String imageBucketName) {
		DuanquConfig.imageBucketName = imageBucketName;
	}
	public static void setVideoBucketName(String videoBucketName) {
		DuanquConfig.videoBucketName = videoBucketName;
	}
	public static void setUpyunUserName(String upyunUserName) {
		DuanquConfig.upyunUserName = upyunUserName;
	}
	public static void setUpyunPassword(String upyunPassword) {
		DuanquConfig.upyunPassword = upyunPassword;
	}
	public static void setUpyunImageDomain(String upyunImageDomain) {
		DuanquConfig.upyunImageDomain = upyunImageDomain;
	}
	public static void setUpyunVideoDomain(String upyunVideoDomain) {
		DuanquConfig.upyunVideoDomain = upyunVideoDomain;
	}
	public static String getWapHost() {
		return wapHost;
	}
	public static void setWapHost(String wapHost) {
		DuanquConfig.wapHost = wapHost;
	}
	public static String getAvatarBucketName() {
		return avatarBucketName;
	}
	public static String getUpyunAvatarDomain() {
		return upyunAvatarDomain;
	}
	public static void setAvatarBucketName(String avatarBucketName) {
		DuanquConfig.avatarBucketName = avatarBucketName;
	}
	public static void setUpyunAvatarDomain(String upyunAvatarDomain) {
		DuanquConfig.upyunAvatarDomain = upyunAvatarDomain;
	}
	public static String getAtMessage() {
		return atMessage;
	}
	public static String getAtMessage2() {
		return atMessage2;
	}
	public static String getShareMessage() {
		return shareMessage;
	}
	public static String getShareMessage2() {
		return shareMessage2;
	}
	public static void setAtMessage(String atMessage) {
		DuanquConfig.atMessage = atMessage;
	}
	public static void setAtMessage2(String atMessage2) {
		DuanquConfig.atMessage2 = atMessage2;
	}
	public static void setShareMessage(String shareMessage) {
		DuanquConfig.shareMessage = shareMessage;
	}
	public static void setShareMessage2(String shareMessage2) {
		DuanquConfig.shareMessage2 = shareMessage2;
	}
	public static String getComeFrom() {
		return comeFrom;
	}
	public static void setComeFrom(String comeFrom) {
		DuanquConfig.comeFrom = comeFrom;
	}
	public static String getAliyunAvatarBucketName() {
		return aliyunAvatarBucketName;
	}
	public static String getAliyunThumbnailBucketName() {
		return aliyunThumbnailBucketName;
	}
	public static String getAliyunVideoBucketName() {
		return aliyunVideoBucketName;
	}
	public static String getAliyunHDVideoBucketName() {
		return aliyunHDVideoBucketName;
	}
	public static String getAliyunAvatarDomain() {
		return aliyunAvatarDomain;
	}
	public static String getAliyunThumbnailDomain() {
		return aliyunThumbnailDomain;
	}
	public static String getAliyunVideoDomain() {
		return aliyunVideoDomain;
	}
	public static String getAliyunHDVideoDomain() {
		return aliyunHDVideoDomain;
	}
	public static void setAliyunAvatarBucketName(String aliyunAvatarBucketName) {
		DuanquConfig.aliyunAvatarBucketName = aliyunAvatarBucketName;
	}
	public static void setAliyunThumbnailBucketName(String aliyunThumbnailBucketName) {
		DuanquConfig.aliyunThumbnailBucketName = aliyunThumbnailBucketName;
	}
	public static void setAliyunVideoBucketName(String aliyunVideoBucketName) {
		DuanquConfig.aliyunVideoBucketName = aliyunVideoBucketName;
	}
	public static void setAliyunHDVideoBucketName(String aliyunHDVideoBucketName) {
		DuanquConfig.aliyunHDVideoBucketName = aliyunHDVideoBucketName;
	}
	public static void setAliyunAvatarDomain(String aliyunAvatarDomain) {
		DuanquConfig.aliyunAvatarDomain = aliyunAvatarDomain;
	}
	public static void setAliyunThumbnailDomain(String aliyunThumbnailDomain) {
		DuanquConfig.aliyunThumbnailDomain = aliyunThumbnailDomain;
	}
	public static void setAliyunVideoDomain(String aliyunVideoDomain) {
		DuanquConfig.aliyunVideoDomain = aliyunVideoDomain;
	}
	public static void setAliyunHDVideoDomain(String aliyunHDVideoDomain) {
		DuanquConfig.aliyunHDVideoDomain = aliyunHDVideoDomain;
	}
	public static List<String> getGroups() {
		return groups;
	}
	public static void setGroups(List<String> groups) {
		DuanquConfig.groups = groups;
	}
	public static Set<String> getImageContentType() {
		return imageContentType;
	}
	public static void setImageContentType(Set<String> imageContentType) {
		DuanquConfig.imageContentType = imageContentType;
	}
	public static String getAliyunSystemImagesBucketName() {
		return aliyunSystemImagesBucketName;
	}
	public static void setAliyunSystemImagesBucketName(
			String aliyunSystemImagesBucketName) {
		DuanquConfig.aliyunSystemImagesBucketName = aliyunSystemImagesBucketName;
	}
	public static String getAliyunSystemImagesDomain() {
		return aliyunSystemImagesDomain;
	}
	public static void setAliyunSystemImagesDomain(String aliyunSystemImagesDomain) {
		DuanquConfig.aliyunSystemImagesDomain = aliyunSystemImagesDomain;
	}
	public static String getCertPath() {
		return certPath;
	}
	public static void setCertPath(String certPath) {
		DuanquConfig.certPath = certPath;
	}
	public static String getAliyunBannerBucketName() {
		return aliyunBannerBucketName;
	}
	public static void setAliyunBannerBucketName(String aliyunBannerBucketName) {
		DuanquConfig.aliyunBannerBucketName = aliyunBannerBucketName;
	}
	public static String getAliyunBannerDomain() {
		return aliyunBannerDomain;
	}
	public static void setAliyunBannerDomain(String aliyunBannerDomain) {
		DuanquConfig.aliyunBannerDomain = aliyunBannerDomain;
	}
	public static String getConvertShellPath() {
		return convertShellPath;
	}
	public static void setConvertShellPath(String convertShellPath) {
		DuanquConfig.convertShellPath = convertShellPath;
	}
	public static String getConvertResultPath() {
		return convertResultPath;
	}
	public static void setConvertResultPath(String convertResultPath) {
		DuanquConfig.convertResultPath = convertResultPath;
	}
	
	
}
