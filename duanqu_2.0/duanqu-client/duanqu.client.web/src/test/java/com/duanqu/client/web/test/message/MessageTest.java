package com.duanqu.client.web.test.message;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.duanqu.client.web.test.BaseTest;

public class MessageTest extends BaseTest{
	
	public static void main(String[] args) {
		MessageTest test = new MessageTest();
		for (int i = 1;i< 30 ;i++){
			test.sendMessage(test.token2,100,"私信测试"+i);
			test.sendMessage(test.token5,3,"私信测试+"+i);
		}
		
//		test.loadDialogs();
		//test.loadMessages();
		//test.loadCommentMessages(test.token);
	}
	
	private void loadCommentMessages(String token ){
		GetMethod get = getGetMethod("message/comments");
		get.setQueryString("token=" +token);
		execute(get);
		printResponseBody(get);
	} 
	
	
	private void sendMessage(String token ,long uid,String messageText){
		PostMethod post = getPostMethod("message/send");
		post.addParameter("token", token);
		post.addParameter("messageText",messageText);
		post.addParameter("recUid",String.valueOf(uid));
		execute(post);
		printResponseBody(post);
	} 
	
	private void loadDialogs(){
		GetMethod get = getGetMethod("message/dialog");
		get.setQueryString("token=" +token);
		int status = execute(get);
		System.out.println(status);
		printResponseBody(get);
	}
	
	private void loadMessages(){
		GetMethod get = getGetMethod("/message/list");
		get.setQueryString("token=" + token+"&uid="+4);
		int status = execute(get);
		System.out.println(status);
		printResponseBody(get);
	}

}
