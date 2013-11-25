package com.duanqu.client.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.duanqu.client.service.comment.BaseTestServiceImpl;
import com.duanqu.client.service.feedback.IClientFeedBackService;
import com.duanqu.common.model.FeedBackModel;

public class TestFeedBackService extends BaseTestServiceImpl {
	@Resource
	IClientFeedBackService clientFeedBackService;
	
	@Test
	@Rollback(false)
	public void testInsertUserFeedBackModel(){
		FeedBackModel feedBackModel=new FeedBackModel();
		feedBackModel.setUid(2);
		feedBackModel.setFeedbackText("test");
		feedBackModel.setIsCheck(0);
		feedBackModel.setScreenshotUrl("url");
		feedBackModel.setCreateTime(System.currentTimeMillis());
		clientFeedBackService.insertUserFeedBackModel(feedBackModel);
	}
	

}
