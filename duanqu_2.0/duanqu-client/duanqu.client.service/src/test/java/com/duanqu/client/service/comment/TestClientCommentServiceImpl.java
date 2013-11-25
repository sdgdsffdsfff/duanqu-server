package com.duanqu.client.service.comment;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.duanqu.common.model.CommentModel;



public class TestClientCommentServiceImpl extends BaseTestServiceImpl {
	@Resource
	IClientCommentService clientCommentService;
	
	
	
	@Rollback(false)
	@Test
	public void testDeleteContentComment(){
		CommentModel commentModel=new CommentModel();
		commentModel.setId(2);
	
		clientCommentService.deleteContentComment(commentModel);
	
	}
	
	public void testInsertContentComment(){
		CommentModel commentModel=new CommentModel();
		commentModel.setId(1);
		commentModel.setUid(1);
		commentModel.setCid(1);
		commentModel.setCommentText("good");
		commentModel.setReplyUid(0);
		commentModel.setCreateTime(System.currentTimeMillis());
		commentModel.setCommentUrl("0");
		commentModel.setParentId(0);
		
		commentModel.setRootId(0);
		clientCommentService.insertContentComment(commentModel);
	}

}
