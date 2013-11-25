package com.duanqu.redis.service.syn.comment;

import java.util.Set;

import com.duanqu.common.PushSender;
import com.duanqu.common.model.CommentModel;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.SettingModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.service.comment.IRedisCommentService;
import com.duanqu.redis.service.content.IRedisContentService;
import com.duanqu.redis.service.message.IRedisMessageService;
import com.duanqu.redis.service.user.IRedisUserService;

public class CommentSynServiceImpl extends BaseRedisService implements ICommentSynService {

	IRedisCommentService redisCommentService;
	IRedisContentService redisContentService;
	IRedisUserService redisUserService;
	IRedisMessageService redisMessageService;
	
	@Override
	public boolean synCommentAdd(CommentModel comment) {
		try{
			redisCommentService.addComment(comment);
			//推送消息到评论消息列表
			redisCommentService.insertUserCommentMessage(comment.getId());
			//推送消息到客户端
			String msg = "";
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
				SettingModel setting = redisUserService.getUserSetting(revUid);
				UserModel revUser = redisUserService.getUser(revUid);
				if (revUser.getDeviceToken() != null && revUser.getDeviceToken().trim().length()>0){
					int count = redisMessageService.countTotalNewMessage(revUid);
					Set<String> tokens = redisUserService.getUserDeviceToken(revUser.getUid());
					if (setting.getCommentMessage() == 1){
						UserModel senderUser = redisUserService.getUser(comment.getUid());
						for (String token : tokens){
							if (token.trim().length() == 64){
								PushSender.send(token, senderUser.getNickName() + msg, count, "qupai://message/comments");
							}
						}
					}else {
						for (String token : tokens){
							if (token.trim().length() == 64){
								PushSender.send(token, null, count, "qupai://message/comments");
							}
						}
					}
				} 
				
			}
			
			return true;
		}catch (Exception e) {
			return false;
		}
		
		
	}

	@Override
	public boolean synCommentDelete(long commentId) {
		return false;
	}

	public void setRedisCommentService(IRedisCommentService redisCommentService) {
		this.redisCommentService = redisCommentService;
	}

	public void setRedisContentService(IRedisContentService redisContentService) {
		this.redisContentService = redisContentService;
	}

	public void setRedisUserService(IRedisUserService redisUserService) {
		this.redisUserService = redisUserService;
	}

	public void setRedisMessageService(IRedisMessageService redisMessageService) {
		this.redisMessageService = redisMessageService;
	}

}
