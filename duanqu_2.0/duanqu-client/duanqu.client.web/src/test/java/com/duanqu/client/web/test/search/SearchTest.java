package com.duanqu.client.web.test.search;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.duanqu.client.web.test.BaseTest;

public class SearchTest extends BaseTest {
	
	void searchSuggest(String key){
		GetMethod get = getGetMethod("suggest/"+key);
		get.setQueryString("token=" + token);
		int status = execute(get);
		System.out.println(status);
		printResponseBody(get);
	} 
	
//	void searchContent(String key){
//		GetMethod get = getGetMethod("content/"+key);
//		get.setQueryString("token=" + token);
//		int status = execute(get);
//		System.out.println(status);
//		printResponseBody(get);
//	} 
	
	
	void searchContent(String key){
		PostMethod post = getPostMethod("search/user");
		post.addParameter("name", key);
		
		post.addParameter("token",token);
		int status = execute(post);
		System.out.println(status);
		printResponseBody(post);
	} 
	
	
	public static void main(String[] args) {
		SearchTest test = new SearchTest();
		test.searchContent("测试");
	}
 
}
