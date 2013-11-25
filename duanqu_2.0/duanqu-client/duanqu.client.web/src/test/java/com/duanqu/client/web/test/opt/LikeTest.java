package com.duanqu.client.web.test.opt;

import org.apache.commons.httpclient.methods.PostMethod;

import com.duanqu.client.web.test.BaseTest;

public class LikeTest extends BaseTest {
	
	public void like(long cid){
		PostMethod post = getPostMethod("opt/like");
		post.addParameter("token", token2);
		post.addParameter("cid",String.valueOf(cid));
		execute(post);
		printResponseBody(post);
	}
	
	public void forward(long cid){
		PostMethod post = getPostMethod("opt/forward");
		post.addParameter("token", token2);
		post.addParameter("cid",String.valueOf(cid));
		execute(post);
		printResponseBody(post);
	}
	
	
	public void feedback(){
		PostMethod post = getPostMethod("feedback");
		post.addParameter("token", token);
		post.addParameter("content","反馈测试！！！");
		execute(post);
		printResponseBody(post);
	}
	
	public void report(long cid){
		PostMethod post = getPostMethod("content/report");
		post.addParameter("token", token);
		post.addParameter("cid",String.valueOf(cid));
		execute(post);
		printResponseBody(post);
	}
	
	
	public static void main(String[] args) {
		LikeTest like = new LikeTest();
		//like.like(49);
		//like.forward(2); 
		long uid = 100000;
		System.out.println(String.valueOf(uid));
	} 

}
 