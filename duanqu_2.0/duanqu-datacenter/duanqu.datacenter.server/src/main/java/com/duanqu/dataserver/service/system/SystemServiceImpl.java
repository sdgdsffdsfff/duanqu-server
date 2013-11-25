package com.duanqu.dataserver.service.system;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.duanqu.client.service.feedback.IClientFeedBackService;
import com.duanqu.client.service.report.IClientReportService;
import com.duanqu.client.service.user.IClientUserService;
import com.duanqu.common.model.FeedBackModel;
import com.duanqu.common.model.OpenFriend;
import com.duanqu.common.model.ReportModel;
import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.service.user.IRedisRelationshipService;
import com.duanqu.redis.utils.key.JMSKeyManager;

public class SystemServiceImpl extends BaseRedisService implements ISystemService {

	IClientFeedBackService clientFeedBackService;
	
	IClientReportService clientReportService;
	
	IClientUserService clientUserService;
	
	IRedisRelationshipService redisRelationshipService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleFeedback() {
		String likeJson = null;
		FeedBackModel feedback = null;
		likeJson = (String)jmsTemplate.boundListOps(JMSKeyManager.getFeedBackListKey()).rightPop();
		while(likeJson != null){
			feedback = JSON.parseObject(likeJson, FeedBackModel.class);
			
			//反馈数据插入数据库
			clientFeedBackService.insertUserFeedBackModel(feedback);
			
			likeJson = (String)jmsTemplate.boundListOps(JMSKeyManager.getFeedBackListKey()).rightPop();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleReport() {
		String json = null;
		ReportModel report = null;
		json = (String)jmsTemplate.boundListOps(JMSKeyManager.getReportListKey()).rightPop();
		while(json != null){
			report = JSON.parseObject(json, ReportModel.class);
			
			// 插入数据库
			clientReportService.insertReportModel(report);
			json = (String)jmsTemplate.boundListOps(JMSKeyManager.getReportListKey()).rightPop();
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleOpenFriendSyn() {
		String json = null;
		OpenFriend open = null;
		json = (String)jmsTemplate.boundListOps(JMSKeyManager.getOpenFriendSynListKey()).rightPop();
		while(json != null){
			open = JSON.parseObject(json, OpenFriend.class);
			// 同步数据
			List<OpenFriend> friends = clientUserService.queryOpenFriendListByUid(open);
			redisRelationshipService.insertNoMatchFriends(open.getUid(),friends,  open.getOpenType());
			json = (String)jmsTemplate.boundListOps(JMSKeyManager.getOpenFriendSynListKey()).rightPop();
		}
	}

	public void setClientFeedBackService(
			IClientFeedBackService clientFeedBackService) {
		this.clientFeedBackService = clientFeedBackService;
	}

	public void setClientReportService(IClientReportService clientReportService) {
		this.clientReportService = clientReportService;
	}

	public void setClientUserService(IClientUserService clientUserService) {
		this.clientUserService = clientUserService;
	}

	public void setRedisRelationshipService(
			IRedisRelationshipService redisRelationshipService) {
		this.redisRelationshipService = redisRelationshipService;
	}

}
