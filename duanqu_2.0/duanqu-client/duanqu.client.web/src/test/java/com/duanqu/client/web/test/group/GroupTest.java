package com.duanqu.client.web.test.group;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.duanqu.client.web.test.BaseTest;

public class GroupTest extends BaseTest {

	void loadGroups() {
		GetMethod get = getGetMethod("group/list");
		get.setQueryString("token=" + token);
		int status = execute(get);
		System.out.println(status);
		printResponseBody(get);
	}
 
	void addGroup(String uids,String groupName){
		PostMethod post = getPostMethod("group/add");
		post.addParameter("token", token);
		post.addParameter("groupName",groupName);
		post.addParameter("uids",uids);
		execute(post);
		printResponseBody(post);
	}  
	 
	void loadGroupUsers(String groupName){
		PostMethod post = getPostMethod("group/users");
		post.addParameter("token", token);
		post.addParameter("groupName",groupName);
		int status = execute(post);
		System.out.println(status);
		printResponseBody(post);
	}
	
	void editGroup(String oldGroupName,String groupName){
		PostMethod post = getPostMethod("group/edit");
		post.addParameter("oldGroupName", oldGroupName);
		post.addParameter("newGroupName", groupName);
		post.addParameter("token", token);
		int status = execute(post);
		System.out.println(status);
		printResponseBody(post);
	}
	
	void deleteGroup(String groupName){
		PostMethod post = getPostMethod("group/delete");
		post.addParameter("groupName", groupName);
		post.addParameter("token", token);
		int status = execute(post);
		System.out.println(status);
		printResponseBody(post);
	}
	
	void addGroupUsers(String groupName,String uids){
		PostMethod post = getPostMethod("group/users/add");
		post.addParameter("groupName", groupName);
		post.addParameter("uids",uids);
		post.addParameter("token", token);
		int status = execute(post);
		System.out.println(status);
		printResponseBody(post);
	} 
	
	void deleteGroupUsers(String groupName,long uid){
		PostMethod post = getPostMethod("group/users/delete");
		post.addParameter("groupName", groupName);
		post.addParameter("uid",String.valueOf(uid));
		post.addParameter("token", token);
		int status = execute(post);
		System.out.println(status);
		printResponseBody(post);
	}
 
	public static void main(String[] args) {
		GroupTest group = new GroupTest();
		group.loadGroups();
		//group.addGroup("2", "test2");
		//group.loadGroupUsers("test");
		//group.editGroup("test","test1"); 
		//group.deleteGroup("test1");
		//group.deleteGroupUsers("Friend2", 2);
		//group.addGroupUsers("未", "2,4,");
	}
}
