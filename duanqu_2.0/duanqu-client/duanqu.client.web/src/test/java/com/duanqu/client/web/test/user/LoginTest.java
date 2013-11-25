package com.duanqu.client.web.test.user;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duanqu.client.web.test.BaseTest;
import com.duanqu.common.Result;
import com.duanqu.common.vo.UserForm;

public class LoginTest extends BaseTest {
	 
	public static void main(String[] args) {
		LoginTest test = new LoginTest();
		test.openBind();
		//test.duanquLogin();
		//test.editUser("蓑笠翁","苦命！！");
		//test.duanquLogin();
		//test.duanquRegister("好人", 1);
	
		
		//Base64 base = new Base64();
		//System.out.println(new String(base.encodeBase64(("duanqu"+System.currentTimeMillis()).getBytes())));
		//test.getMobileCode();
		//test.checkMobileCode("6506");
//		Set tag = new HashSet();
//		tag.add("asdf");
//		tag.add("432424");
//		System.out.println(tag.toString());
		
	}    
	  
	public void getMobileCode(){
		try { 
			long begin = System.currentTimeMillis();
			
			PostMethod post = getPostMethod("mobile/code/get");
			post.addParameter("mobile", "13867482066");
			post.addParameter("token",token);
			execute(post);
			printResponseBody(post);
			post.releaseConnection();
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkMobileCode(String code){
		try { 
			long begin = System.currentTimeMillis();
			
			PostMethod post = getPostMethod("mobile/bind");
			post.addParameter("mobile", "13666698085");
			post.addParameter("code", code);
			post.addParameter("token",token);
			execute(post);
			printResponseBody(post);
			post.releaseConnection();
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void duanquLogin(){
		try { 
			long begin = System.currentTimeMillis();
			
			PostMethod post = getPostMethod("user/duanqu/login");
			post.addParameter("email", "badboy1@danqoo.com");
			post.addParameter("loginPassword","badboy4471");
			execute(post);
			printResponseBody(post);
			post.releaseConnection();
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	
	public void openLogin(){
		try { 
			long begin = System.currentTimeMillis();
			
			PostMethod post = getPostMethod("user/open/login");
			post.addParameter("openType", "1");
			post.addParameter("openUid","1421024527");
			post.addParameter("accessToken", "123123123");
			post.addParameter("refreshToken", "123123123");
			post.addParameter("expiresIn", "12345");
			execute(post);
			printResponseBody(post); 
			post.releaseConnection();
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	
	public UserForm duanquRegister(String s,int i){
		UserForm user = null;
		try { 
			long begin = System.currentTimeMillis();
			
			PostMethod post = getPostMethod("user/duanqu/register");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			post.addParameter("email", "badboy"+i+"@danqoo.com");
			post.addParameter("loginPassword","badboy4471");
			post.addParameter("nickName",s+s+s+i);
			post.addParameter("longitude","100.0");
			post.addParameter("latitude","200.0");
			
			execute(post);
			String result = printResponseBody(post); 
			if (result != null){
				Result obj = JSON.parseObject(result, Result.class);
				JSONObject object = (JSONObject)obj.getData();
				user = new UserForm();
				user.setAvatar(object.getString("avatar"));
				user.setToken(object.getString("token"));
				user.setNickName(object.getString("nickName"));
				user.setUid(Long.parseLong(object.getString("uid")));
			}
			post.releaseConnection();
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public void openBind(){
		try { 
			long begin = System.currentTimeMillis();
			//MultipartPostMethod post = new MultipartPostMethod(host+"user/open/bind");
			MultipartPostMethod post = new MultipartPostMethod(host+"user/open/direct/login");
			
			
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			post.addParameter("email", "wanghh@danqoo.com");
			post.addParameter("loginPassword","badboy4471");
			post.addPart(new StringPart("nickName","王海华","utf-8"));
			post.addParameter("longitude","100.0");
			post.addParameter("latitude","200.0");
			
			post.addParameter("openType", "1");
			post.addParameter("openUserid","1421024527");
			post.addParameter("accessToken","2.0085TKYB0kAFJeb866bbcb760JctUW");
			post.addParameter("refreshToken","123457890");
			post.addParameter("expiresIn","347890");
			
			File avatar = new File("/home/tiger/123.jpg");
			post.addPart(new FilePart("avatar", avatar));
			
			
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
	 
	public void openBindOther(){
		try { 
			long begin = System.currentTimeMillis();
			PostMethod post = new PostMethod(host+"user/open/bind/other");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			
			post.addParameter("openType", "1");
			post.addParameter("openUserid","1421024527");
			post.addParameter("accessToken","2.0085TKYB0kAFJeb866bbcb760JctUW");
			post.addParameter("refreshToken","123457890");
			post.addParameter("expiresIn","347890");
			post.addParameter("token","c37953b3061f340ba5310ea5e88dcfcd");
			
			
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
	
	@SuppressWarnings("deprecation")
	public void editUser(String nickName,String signature){
		try { 
			long begin = System.currentTimeMillis();
			MultipartPostMethod post = super.getMultipartPostMethod("user/edit");
			post.addPart(new StringPart("nickName","蓑笠翁","utf-8"));
			post.addPart(new StringPart("signature","浙江杭州北部软件园","utf-8"));
			post.addParameter("token",token);
			post.addPart(new FilePart("avatar", new File("/home/wanghaihua/test/123.jpg"),"image/jpeg",null) );
			execute(post);
			printResponseBody(post);
			post.releaseConnection();
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
