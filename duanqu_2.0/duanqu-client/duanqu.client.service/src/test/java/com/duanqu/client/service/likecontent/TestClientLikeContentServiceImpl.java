package com.duanqu.client.service.likecontent;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.duanqu.client.service.comment.BaseTestServiceImpl;
import com.duanqu.client.service.forwardcontent.IClientForwardContentService;
import com.duanqu.common.model.LikeContentModel;
import com.duanqu.common.model.ForwardContentModel;
public class TestClientLikeContentServiceImpl extends BaseTestServiceImpl {
	
	@Resource
	IClientLikeContentService clientLikeContentService;
	
	@Resource
	IClientForwardContentService clientForwardContentService;
	
	//@Test
	@Rollback(false)
	public void testInsertLikeContentModel(){
		
		LikeContentModel likeContentModel=new LikeContentModel();
		likeContentModel.setCid(10);
		likeContentModel.setUid(1);
		likeContentModel.setCreateTime(System.currentTimeMillis());
		clientLikeContentService.insertLikeContentModel(likeContentModel);
		
	}
	//@Test
	@Rollback(false)
	public void testDeleteLikeContentModel(){
		
		LikeContentModel likeContentModel=new LikeContentModel();
		likeContentModel.setCid(10);
		likeContentModel.setUid(1);
		clientLikeContentService.deleteLikeContentModel(likeContentModel);
		
	}
	@Test
	@Rollback(false)
   public void testInsertForwardContentModel(){
		
		ForwardContentModel forwardContentModel=new ForwardContentModel();
		forwardContentModel.setCid(10);
		forwardContentModel.setUid(1);
		forwardContentModel.setCreateTime(System.currentTimeMillis());
		clientForwardContentService.insertForwardContentModel(forwardContentModel);
		
	}
   public void testDeleteForwardContentModel(){
		
		ForwardContentModel forwardContentModel=new ForwardContentModel();
		forwardContentModel.setCid(10);
		forwardContentModel.setUid(1);
		forwardContentModel.setCreateTime(System.currentTimeMillis());
		clientForwardContentService.insertForwardContentModel(forwardContentModel);
		
	}

}
