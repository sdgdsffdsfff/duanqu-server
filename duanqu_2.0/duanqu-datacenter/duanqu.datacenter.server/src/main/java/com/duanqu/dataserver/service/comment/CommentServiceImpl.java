package com.duanqu.dataserver.service.comment;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.duanqu.client.service.comment.IClientCommentService;
import com.duanqu.common.PushSender;
import com.duanqu.common.model.CommentModel;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.SettingModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.service.comment.IRedisCommentService;
import com.duanqu.redis.service.content.IRedisContentService;
import com.duanqu.redis.service.message.IRedisMessageService;
import com.duanqu.redis.service.timeline.IRedisTimelineService;
import com.duanqu.redis.service.user.IRedisUserService;
import com.duanqu.redis.utils.key.JMSKeyManager;

public class CommentServiceImpl extends BaseRedisService implements ICommentService {
	
	Log logger = LogFactory.getLog(CommentServiceImpl.class);
	
	IRedisCommentService redisCommentService;
	
	IClientCommentService clientCommentService;
	
	IRedisUserService redisUserService;
	
	IRedisContentService redisContentService;
	
	IRedisMessageService redisMessageService;
	
	IRedisTimelineService redisTimelineService;

	@SuppressWarnings("unchecked")
	@Override
	public void handleCommentAdd() {
		String json = null;
		CommentModel comment = null;
		json = (String)jmsTemplate.boundListOps(JMSKeyManager.getCommentListKey()).rightPop();
		while(json != null){
			comment = JSON.parseObject(json, CommentModel.class);
			
			//更新内容操作用户
			redisTimelineService.refreshOptUsersCache(comment.getCid());
			
			//推送消息到评论消息列表
			redisCommentService.insertUserCommentMessage(comment.getId());
			String msg = "";
			
			//推送消息到客户端
			long revUid = 0;
			if (comment.getParentId() == 0){
				//直接评论
				ContentModel content = redisContentService.getContent(comment.getCid());
				msg = "评论了我的内容";
				revUid = content.getUid();
			}else{
				revUid = comment.getReplyUid();
				msg = "回复了我的评论";
			}
			
			if (revUid != 0) {
				UserModel revUser = redisUserService.getUser(revUid);
				if (revUser != null && revUser.getDeviceToken() != null
						&& revUser.getDeviceToken().trim().length() >= 64) {
					int count = redisMessageService.countTotalNewMessage(revUid);
					Set<String> tokens = redisUserService.getUserDeviceToken(revUser.getUid());
					SettingModel setting = redisUserService.getUserSetting(revUid);
					if (setting.getCommentMessage() == 1) {
						UserModel senderUser = redisUserService.getUser(comment.getUid());
						if (revUser.getDeviceToken() != null && revUser.getDeviceToken().trim().length() > 0) {
							for (String token : tokens) {
								if (token.trim().length() == 64) {
									PushSender.send(token, senderUser.getNickName()	+ msg, count, "qupai://message/comments");
								}
							}
						}
					}else{
						if (revUser.getDeviceToken() != null && revUser.getDeviceToken().trim().length() > 0) {
							for (String token : tokens) {
								if (token.trim().length() == 64) {
									PushSender.send(token, null, count, "qupai://message/comments");//只推送角标数字
								}
							}
						}
					}
				}
			}
			try{
				//插入数据库
				clientCommentService.insertContentComment(comment);
			}catch(Exception e){
				logger.error("评论入库失败：Message="+e.getMessage()+";Params="+comment);
			}
			
			json = (String)jmsTemplate.boundListOps(JMSKeyManager.getCommentListKey()).rightPop();
		}
	}
	
	@Override
	public void handelCommentDelete(long id) {
		CommentModel commentModel = redisCommentService.getComment(id);
		if (commentModel!= null && commentModel.getId()>0){
			//删除评论
			int count = redisCommentService.deleteComment(commentModel);
			//更新内容操作用户
			redisTimelineService.refreshOptUsersCache(commentModel.getCid());
		}else{
			commentModel = new CommentModel();
			commentModel.setId(id);
		}
		try{
			clientCommentService.deleteContentComment(commentModel);
		}catch(Exception e){
			logger.error("删除评论出错！"+e);
		}
		
	}

	public void setRedisCommentService(IRedisCommentService redisCommentService) {
		this.redisCommentService = redisCommentService;
	}

	public void setClientCommentService(IClientCommentService clientCommentService) {
		this.clientCommentService = clientCommentService;
	}

	public void setRedisUserService(IRedisUserService redisUserService) {
		this.redisUserService = redisUserService;
	}

	public void setRedisContentService(IRedisContentService redisContentService) {
		this.redisContentService = redisContentService;
	}

	public void setRedisMessageService(IRedisMessageService redisMessageService) {
		this.redisMessageService = redisMessageService;
	}

	public void setRedisTimelineService(IRedisTimelineService redisTimelineService) {
		this.redisTimelineService = redisTimelineService;
	}
}
