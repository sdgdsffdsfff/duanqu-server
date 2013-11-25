package com.duanqu.client.web.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;
import com.duanqu.common.Result;

public class UploadTest extends BaseTest {

	private static BASE64Encoder base64 = new BASE64Encoder();
      
	public static void main(String... strings) {
		
	/*	MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-1");
			sha.update("123sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss213".getBytes()); 
			String result = new String(sha.digest());
			System.out.println(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}  */
	     
		
		
		//String host = "http://192.168.10.204:8080";
		//String host = "http://42.120.60.106";
		//String host = "http://mimg.danqoo.com:8090";
		File video = new File("/home/tiger/redis.conf");
//		File thumbnails = new File("/home/tiger/181370775584952.jpg");
		UploadTest test = new UploadTest();
		//http://112.124.2.89/duanqu.upload/content/upload
//		test.uploadFile(video, thumbnails, "http://localhost:8080/duanqu.upload/");
		test.resumeUploadFile(video, "123213213213123");
		//test.uploadFile(video, thumbnails, "http://192.168.10.204:8080/duanqu.upload/");
		/*try  {
			test.getHead();  
		} catch (IOException e) { 
			e.printStackTrace();
		}*/       
	/*	   
		File file = new File("/home/wanghaihua/123/456/789","1.jpg");
		if (!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}*/
//		LikeTest like = new LikeTest();
//		for (int i = 0;i<20;i++){
//			long cid = test.uploadFile(video, thumbnails, "http://192.168.10.204:8080/duanqu.upload/");
//			if (cid != 0){
//				like.like(cid);
//			}
//		}
//		System.out.println(test.uploadFile(video, thumbnails, "http://192.168.10.204:8080/duanqu.upload/"));
		
	}    
  
	
	private void getHead() throws IOException{
		HeadMethod head = new HeadMethod("http://211.155.224.10:8083/1.mp4");
		HttpClient client = new HttpClient();
		client.setConnectionTimeout(1000 * 60);
		int status = 0;
		try {
			status = client.executeMethod(head);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		if (status == HttpStatus.SC_OK) {
			System.out.println(head.getResponseBodyAsString());
		} else {
			System.out.println(head.getResponseBodyAsString());
		}
		head.releaseConnection();
	}
	
	private void getOption() throws IOException{
		OptionsMethod head = new OptionsMethod("http://211.155.224.10:8083/1.mp4");
		HttpClient client = new HttpClient();
		client.setConnectionTimeout(1000 * 60);
		int status = 0;
		try {
			status = client.executeMethod(head);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (status == HttpStatus.SC_OK) {
			System.out.println(head.getResponseBodyAsString());
		} else {
			System.out.println(head.getResponseBodyAsString());
		}
		head.releaseConnection();
	}
	
	private void uploadFileByBase64(File file, String host) {
		try {
			String url = "/duanqu/content/upload";
			long begin = System.currentTimeMillis();
			FileInputStream inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			PostMethod post = new PostMethod(host+url);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			// PostMethod post = new
			// PostMethod("http://localhost:8080/duanqu/content/upload");
			post.addParameter("video", base64.encode(buffer));
			HttpClient client = new HttpClient();
			client.setConnectionTimeout(1000 * 60);
			int status = 0;
			try {
				status = client.executeMethod(post);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (status == HttpStatus.SC_OK) {
				System.out.println(post.getResponseBodyAsString());
			} else {
				System.out.println(post.getResponseBodyAsString());
			}
			post.releaseConnection();
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * private String tags;// 标签串，以“;”隔开
	private String atUserDuanqu; // @Duanqu 用户
	private String atUserSina; // @Sina 用户
	private String atUserTencent; // @腾讯用户
	private String shareSina; // 分享新浪
	private String shareTencent;// 分享腾讯
	private int isPrivate;// 0=公开？1=私密？
	private float longitude;// 经度
	private float latitude;// 纬度
	private String createtime;// 拍摄时间
	private float playTime;// 视频时长
	private float width;// 宽度
	private float height;// 高度
	private String location;// 地理位置（商业场所）
	 * @param video
	 * @param thumbnails 
	 * @param host
	 */   
	

	private long uploadFile(File video,File thumbnails, String host) {
		long begin = System.currentTimeMillis();
		String url = "content/upload";
		MultipartPostMethod filePost = new MultipartPostMethod(host+url); // 若没有commons-codec-1.4-bin.zip,
		//filePost.getParams().setContentCharset("utf-8");
		filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
	//	filePost.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
		try {  
			//filePost.addParameter("description","只能顾问说明");
			filePost.addParameter("tags","");
			filePost.addParameter("atUserDuanqu","");
			filePost.addParameter("atUserSina","");
			filePost.addParameter("atUserTencent","");
			filePost.addParameter("shareSina","0");
			filePost.addParameter("shareTencent","0");
			filePost.addParameter("isPrivate","0");
			filePost.addParameter("longitude","100.0");
			filePost.addParameter("latitude","0");
			filePost.addParameter("createtime","1372151106491");
			filePost.addParameter("playTime","8.074729");//8.075507
			filePost.addParameter("width","480"); 
			filePost.addParameter("height","480");
			filePost.addPart(new StringPart("location","浙江杭州北部软件园","utf-8"));
			filePost.addPart(new StringPart("description","法轮功中文中文测试,变态","utf-8"));
			filePost.addPart(new FilePart("video", video,"video/mp4","utf-8"));
			filePost.addPart(new FilePart("thumbnails", thumbnails,"image/jpeg","utf-8") );
			filePost.addParameter("token","e3178381a223fbc1c51d0dbe1d5ba0fc");
			
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {
				try{
					String json = filePost.getResponseBodyAsString();
					Result result = JSON.parseObject(json,Result.class);
					System.out.println(result.getData().toString());
					return 0;
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				
			} else {
				System.out.println(filePost.getResponseBodyAsString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}

		long end = System.currentTimeMillis();
		System.out.println(end - begin);
		return 0;
	}
	
	
	private long resumeUploadFile(File video, String key) {
		long begin = System.currentTimeMillis();
		String url = "resume/upload/test";
		MultipartPostMethod filePost = new MultipartPostMethod(host+url); // 若没有commons-codec-1.4-bin.zip,
		//filePost.getParams().setContentCharset("utf-8");
		filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
	//	filePost.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
		try {  
			//filePost.addParameter("description","只能顾问说明");
			filePost.addParameter("key","123456789");
			filePost.addPart(new FilePart("video", video,"video/mp4","utf-8"));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {
				try{
					String json = filePost.getResponseBodyAsString();
					Result result = JSON.parseObject(json,Result.class);
					System.out.println(result.getData().toString());
					return 0;
				}catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println(filePost.getResponseBodyAsString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
		return 0;
	}

}
