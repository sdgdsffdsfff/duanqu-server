package com.duanqu.client.web.test.black;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.duanqu.client.web.test.BaseTest;

public class TestBlackList extends BaseTest {

	public void addBlackList(String token ,long uid){
		try { 
			long begin = System.currentTimeMillis();
			PostMethod post = getPostMethod("user/black/add");
			post.addParameter("uid", String.valueOf(uid));
			post.addParameter("token",token);
			
			int status = execute(post);
			System.out.println(status);
			printResponseBody(post);
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelBlackList(String token ,long uid){
		try { 
			long begin = System.currentTimeMillis();
			PostMethod post = getPostMethod("/user/black/cancel");
			post.addParameter("uid", String.valueOf(uid));
			post.addParameter("token",token);
			int status = execute(post);
			System.out.println(status);
			printResponseBody(post);
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void loadBlackList(String token ,int page){
		GetMethod get = getGetMethod("/user/black/list");
		get.setQueryString("token=" + token+"&page="+page);
		int status = execute(get);
		System.out.println(status);
		printResponseBody(get);
	}
	
	public static void main(String[] args) {
		TestBlackList test = new TestBlackList();
		long uid = 21;
		//test.addBlackList(test.token, uid);
		
		//test.loadBlackList(test.token, 0);
		
		test.cancelBlackList(test.token, uid);
		test.loadBlackList(test.token, 0);
	}
}
