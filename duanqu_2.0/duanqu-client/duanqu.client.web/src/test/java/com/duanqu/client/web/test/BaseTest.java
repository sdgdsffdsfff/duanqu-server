package com.duanqu.client.web.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class BaseTest {
	protected String host = "http://localhost:8080/duanqu.upload/";
//	 
//	protected String token = "2a36d0b507904a60d1a753f439a6762c"; //uid = 200
//
//	protected String token2 = "882b8dd3196308fca887087343510b83";// uid = 2
//	   
//	protected String token3 = "977574f8881d44152feef4a517bed0dc";//uid = 3
//	
//	protected String token4 = "ae67f4d2c82e7a6b243cfc99aa4dd72f";//uid = 4
//
//	protected String token5 = "d0470beee236c32dea634b661289ec2a";//uid = 5
	  
//	protected String host = "http://192.168.10.204:8080/duanqu/";
//	protected String host = "http://client.danqoo.com/";
	
	protected String token = "58d9c0d796eed4890955698b2f62576d"; //uid = 8 测试
	  
	protected String token2 = "05a846fd4408027373bd91b4a6d11c05";// uid = 3 //欢英
	
	protected String token3 = "4195aee332cea00447ecf1069b69a070";//uid = 101
	
	protected String token4= "1e4c4a090c5470bca327ef314a3ee244";//uid 104
	
	protected String token5 = "e128d50d4d3a6a0e93238633987628a7";//uid 100
   
	protected PostMethod getPostMethod(String method) { 
		PostMethod post = new PostMethod(host + method);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"utf-8");
		return post; 
	} 
	
	
	protected MultipartPostMethod getMultipartPostMethod(String method) { 
		MultipartPostMethod post = new MultipartPostMethod(host + method);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"utf-8");
		return post; 
	}  
	
	protected GetMethod getGetMethod(String method) {
		GetMethod get = new GetMethod(host + method);
		get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"utf-8");
		return get;
	}
 
	protected int execute(HttpMethod method) {
		HttpClient client = new HttpClient();
		int status = 0;
		try {
			status = client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}

	protected String printResponseBody(HttpMethod method) {
		try {
			String json = method.getResponseBodyAsString();
			System.out.println(json);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
 
}
