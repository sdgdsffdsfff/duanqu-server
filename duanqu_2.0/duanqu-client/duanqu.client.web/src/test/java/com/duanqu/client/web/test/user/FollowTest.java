package com.duanqu.client.web.test.user;

import org.apache.commons.httpclient.methods.PostMethod;

import com.duanqu.client.web.test.BaseTest;

public class FollowTest extends BaseTest {
  
	public void follow(String token ,long uid){
		try { 
			long begin = System.currentTimeMillis();
			PostMethod post = getPostMethod("user/follow");
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
	
	public void unFollow(long uid){
		try { 
			long begin = System.currentTimeMillis();
			PostMethod post = getPostMethod("user/unfollow");
			post.addParameter("uid", String.valueOf(uid));
			post.addParameter("token",token2);
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
	 
	public void loadFollows(){
		try { 			 
			long begin = System.currentTimeMillis();
			PostMethod post = getPostMethod("user/follows");
			post.addParameter("uid", "0");
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
	public static void main(String[] args) {
		FollowTest follow = new FollowTest();
		/*String[] arr = {"阿","悲","车","点","额","发","改","河","给","开","路","没","男","哦","皮","取","人","树","天","我","小","要","在"};
		int length = arr.length;
		LoginTest login = new LoginTest();
		for (int i = 102;i<200;i++){
			UserForm user = login.duanquRegister(arr[i%length],i+200);
			if (user != null && user.getUid()>0){
				follow.follow(follow.token,user.getUid());
				follow.follow(user.getToken(), 1);
			}
			//System.out.println(arr[i%27]);
		}*/
		 
		//follow.follow(follow.token, 49);
		//follow.unFollow(1);
		//System.out.println(System.getProperty("user.dir"));
		
	}
}
