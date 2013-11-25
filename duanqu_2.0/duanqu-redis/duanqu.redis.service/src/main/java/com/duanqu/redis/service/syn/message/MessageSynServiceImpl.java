package com.duanqu.redis.service.syn.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.lf5.viewer.LogFactor5Dialog;
import org.json.JSONException;

import com.duanqu.common.JumpUrl;
import com.duanqu.common.PushSender;
import com.duanqu.common.model.MessageModel;
import com.duanqu.common.model.RedisMessageModel;
import com.duanqu.common.model.SettingModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.service.message.IRedisMessageService;
import com.duanqu.redis.service.user.IRedisUserService;

public class MessageSynServiceImpl extends BaseRedisService implements IMessageSynService {
	Log logger = LogFactory.getLog(MessageSynServiceImpl.class);
	
	IRedisMessageService redisMessageService;
	IRedisUserService redisUserService;

	@Override
	public boolean synMessageSend(MessageModel message) {
		try {
			// 插入私信详细信息
			message = redisMessageService.insertMessage(message);
			// 插入对话列表
			redisMessageService.insertDialog(message.getUid(), message.getRecUid());
			// 插入对话详细列表
			RedisMessageModel redisModel = message.asRedisForm();
			redisMessageService.insertDialogMessage(redisModel);
			
			//推送私信消息到客户端
			SettingModel setting = redisUserService.getUserSetting(message.getRecUid());
			
				UserModel revUser = redisUserService.getUser(message.getRecUid());
			if (revUser != null && revUser.getDeviceToken() != null
					&& revUser.getDeviceToken().trim().length() >= 64) {
				int count = redisMessageService.countTotalNewMessage(revUser.getUid());
				Set<String> tokens = redisUserService.getUserDeviceToken(revUser.getUid());
				if (setting.getMessage() == 1) {
					UserModel senderUser = redisUserService.getUser(message.getUid());
					for (String token : tokens) {
						if (token.trim().length() == 64) {
							try{
								PushSender.send(token, senderUser.getNickName()	+ "给我发了私信", count, "qupai://message/dialog");
							}catch (Exception e) {
								logger.error("推送消息出错！"+e.getMessage());
							}
							
						}
					}
				} else {
					for (String token : tokens) {
						if (token.trim().length() == 64) {
							PushSender.send(token, null, count, "qupai://message/dialog");
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public boolean synMessageSendBatchs(MessageModel message) {
		try {
			// 插入私信详细信息
			message = redisMessageService.insertMessage(message);
			// 插入对话列表
			redisMessageService.insertDialog(message.getUid(), message.getRecUid());
			// 插入对话详细列表
			RedisMessageModel redisModel = message.asRedisForm();
			redisMessageService.insertDialogMessage(redisModel);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	public List<PayloadPerDevice> toList(List<Long> list){
		List<PayloadPerDevice> listPlay = new ArrayList<PayloadPerDevice>();
		
		//推送私信消息到客户端
		for(Long recUid:list){
		PushNotificationPayload payload = new PushNotificationPayload();
		PayloadPerDevice pay;
		SettingModel setting = redisUserService.getUserSetting(recUid);
		UserModel revUser = redisUserService.getUser(recUid);
		if (revUser != null && revUser.getDeviceToken() != null
				&& revUser.getDeviceToken().trim().length() >= 64) {
			int count = redisMessageService.countTotalNewMessage(revUser.getUid());
			Set<String> tokens = redisUserService.getUserDeviceToken(revUser.getUid());
			if (setting.getMessage() == 1) {
				UserModel senderUser = redisUserService.getUser(recUid);
				for (String token : tokens) {
					if (token.trim().length() == 64) {
						try {
							payload.addAlert(senderUser.getNickName()+ "给我发了私信");
							payload.addSound("default");// 声音
							payload.addBadge(count);
							payload.addCustomDictionary("url", "qupai://message/dialog");// 添加字典
                            pay= new PayloadPerDevice(payload,token );// 将要推送的消息和手机唯一标识绑定
							listPlay.add(pay);
						} catch (Exception e) {
							logger.error("消息推送出错！"+e.getMessage());
						}
					}
				}
			} else {
				for (String token : tokens) {
					if (token.trim().length() == 64) {
						try {
							payload.addSound("default");// 声音
							payload.addBadge(count);// 图标小红圈的数值
							payload.addCustomDictionary("url", "qupai://message/dialog");// 添加字典
                            pay= new PayloadPerDevice(payload,token );// 将要推送的消息和手机唯一标识绑定
							listPlay.add(pay);
						} catch (Exception e) {
							logger.error("消息推送出错！"+e.getMessage());
						}
					}
				}
			}
		}
		}
		return listPlay;
	}
	
	@Override
	public void batchSynMessageSend(MessageModel message) {
		try {
			// 插入私信详细信息
			message = redisMessageService.insertMessage(message);
			// 插入对话列表
			redisMessageService.insertDialog(message.getUid(), message.getRecUid());
			// 插入对话详细列表
			RedisMessageModel redisModel = message.asRedisForm();
			redisMessageService.insertDialogMessage(redisModel);
			
			//推送私信消息到客户端
			SettingModel setting = redisUserService.getUserSetting(message.getRecUid());
			
				UserModel revUser = redisUserService.getUser(message.getRecUid());
			if (revUser != null && revUser.getDeviceToken() != null
					&& revUser.getDeviceToken().trim().length() >= 64) {
				int count = redisMessageService.countTotalNewMessage(revUser.getUid());
				Set<String> tokens = redisUserService.getUserDeviceToken(revUser.getUid());
				if (setting.getMessage() == 1) {
					UserModel senderUser = redisUserService.getUser(message.getUid());
					for (String token : tokens) {
						if (token.trim().length() == 64) {
							try{
								PushSender.send(token, senderUser.getNickName()	+ "给我发了私信", count, "qupai://message/dialog");
							}catch (Exception e) {
								logger.error("推送消息出错！"+e.getMessage());
							}
						}
					}
				} else {
					for (String token : tokens) {
						if (token.trim().length() == 64) {
							PushSender.send(token, null, count, "qupai://message/dialog");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("批量发送私信出错！e="+e);
		}
	}

	public void setRedisMessageService(IRedisMessageService redisMessageService) {
		this.redisMessageService = redisMessageService;
	}

	public void setRedisUserService(IRedisUserService redisUserService) {
		this.redisUserService = redisUserService;
	}
	@Override
	public PayloadPerDevice praseMessage(long uid, String message,String innerParam,String type) {

		PushNotificationPayload payload = new PushNotificationPayload();
		PayloadPerDevice pay = null;
		UserModel revUser = redisUserService.getUser(uid);
		if (revUser != null && revUser.getDeviceToken() != null
				&& revUser.getDeviceToken().trim().length() >= 64) {
			Set<String> tokens = redisUserService.getUserDeviceToken(revUser.getUid());
			for (String token : tokens) {
				if (token.trim().length() == 64) {
					try {
						payload.addAlert(message);
						payload.addSound("default");// 声音
						payload.addCustomDictionary("url",JumpUrl.valueOf(type).getUrl()+innerParam);// 添加字典
						pay = new PayloadPerDevice(payload, token);// 将要推送的消息和手机唯一标识绑定
					} catch (Exception e) {
						logger.error("消息封装出错！" + e.getMessage());
					}
				}
			}
		}
		return pay;
	}

	
}
