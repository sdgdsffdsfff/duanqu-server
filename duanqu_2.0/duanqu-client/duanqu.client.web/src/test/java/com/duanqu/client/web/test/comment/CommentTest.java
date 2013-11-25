package com.duanqu.client.web.test.comment;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.duanqu.client.web.test.BaseTest;

public class CommentTest extends BaseTest{
	/**
	 * private long cid;//内容ID
	private String commentText;//评论内容
	private long parentId;//父评论Id
	private long rootId;//根评论ID
	private long replyUid;//回复用户ID
	 * @param uids
	 * @param groupName
	 */ 
	void addComment(long parentId,long replyUid,String curToken,String text){
		PostMethod post = getPostMethod("comment/add");
		post.addParameter("token", curToken);
		post.addParameter("cid","93");
		post.addParameter("commentText",text);
		post.addParameter("parentId",String.valueOf(parentId));
		post.addParameter("replyUid", String.valueOf(replyUid));
		execute(post); 
		printResponseBody(post);
	} 
	
	void loadComment(long cid){
		GetMethod get = getGetMethod("comment/list");
		get.setQueryString("token=" + token+"&cid="+cid);
		int status = execute(get);
		System.out.println(status);
		printResponseBody(get);
	}
	
	void delComment(long id){
		PostMethod post = getPostMethod("comment/delete");
		post.addParameter("token", "a8e000b53c65b9a704405dbc1aac871d");
		post.addParameter("id", String.valueOf(id));
		execute(post);
		printResponseBody(post);
	} 
  
	public static void main(String[] args) {
		CommentTest comment = new CommentTest();
		//comment.addComment(23,137,"4ac6604c4bfda7f9c2f0c3b3d8a163fb","这是回复评论--11");
//		comment.addComment(0,0,comment.token,"变态移除有序集key中，所有score值介于min和max之间(包括等于min或max)的成员。！");
//		comment.delComment(7491);7161,7490,7162,7160,7159,7158,7157,7156,6375,6374,6371,6370
		//6369,5929,5928,5927,5926,5924,5758,5587,5583,5581,5580,5579
		comment.delComment(5579);
		//comment.loadComment(8);
	}
}
