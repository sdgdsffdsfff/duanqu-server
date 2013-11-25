package com.duanqu.dataserver.jms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.duanqu.client.service.group.IClientGroupService;
import com.duanqu.client.service.message.IClientMessageService;
import com.duanqu.client.service.user.IClientUserService;
import com.duanqu.common.EmojiUtils;
import com.duanqu.common.index.IIndexBuilder;
import com.duanqu.common.model.BindModel;
import com.duanqu.common.model.IndexUserModel;
import com.duanqu.common.model.MessageModel;
import com.duanqu.common.model.OpenFriend;
import com.duanqu.common.model.RedisMessageModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.share.IShareService;
import com.duanqu.redis.service.group.IRedisGroupService;
import com.duanqu.redis.service.jms.IRedisJMSMessageService;
import com.duanqu.redis.service.message.IRedisMessageService;
import com.duanqu.redis.service.timeline.IRedisTimelineService;
import com.duanqu.redis.service.user.IRedisRelationshipService;
import com.duanqu.redis.service.user.IRedisUserService;

public class ConsumerUserServiceImpl implements MessageListener {

	private static long QUPAI_UID = 1; // 趣拍用户ID

	Log logger = LogFactory.getLog(ConsumerUserServiceImpl.class);

	IRedisUserService redisUserService;

	IRedisRelationshipService redisRelationshipService;

	IShareService shareService;

	IClientUserService clientUserService;

	IRedisGroupService redisGroupService;

	IClientGroupService clientGroupService;

	IRedisMessageService redisMessageService;

	IClientMessageService clientMessageService;

	IRedisJMSMessageService redisJMSMessageService;

	IRedisTimelineService redisTimelineService;

	IIndexBuilder indexBuilder;

	public void onMessage(Message message) {
		// 这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换
		if (message instanceof ActiveMQObjectMessage) {
			try {
				ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
				UserModel user = (UserModel) msg.getObject();
				System.out.println("======Begin consumer");
				if (user != null) {
					user.setNickName(EmojiUtils.filterEmoji(user.getNickName()));// 对特殊字符进行编码
					user.setRoleId(3);// 注册的为普通用户
					// 插入用户基本信息
					BindModel bind = null;
					bind = redisUserService.getBindInfo(user.getUid(),
							BindModel.OpenType.SINA.getMark());
					if (bind == null || bind.getUid() == 0) {
						bind = redisUserService.getBindInfo(user.getUid(),
								BindModel.OpenType.TENCENT.getMark());
					}
					try {
						// 插入数据库
						if (bind != null && bind.getUid() > 0) {
							bind.setOpenNickName(EmojiUtils.filterEmoji(bind
									.getOpenNickName()));
							clientUserService.insertRegisterTh(user, bind);
						} else {
							clientUserService.insertRegister(user);
						}
					} catch (Exception e) {
						logger.error("用户信息插入数据库出错：Params=" + user
								+ ";Error Message=" + e);
					}

					// 自动关注趣拍官方帐号,趣拍（短趣君）的数据采用拉的方式
					redisRelationshipService.follow(user.getUid(), 1);
					// 官方帐号自动关注新用户
					redisRelationshipService.follow(1, user.getUid());
					// 新用户和趣拍相互关注
					try {
						clientUserService.followEachOther(QUPAI_UID,
								user.getUid());
						// 插入异步更新消息队列 主要同步用户粉丝数和关注数
						redisRelationshipService
								.insertUserUpdateQueue(QUPAI_UID);
						redisRelationshipService.insertUserUpdateQueue(user
								.getUid());
					} catch (Exception e) {
						logger.error("新用户和趣拍相互关注插入数据库出错！Error=" + e);
					}

					// 推送一条趣拍最新的内容给新用户
					redisTimelineService.pushOneContentsToNewUser(user.getUid(), 1);

					List<OpenFriend> friends = new ArrayList<OpenFriend>();
					if (bind != null && bind.getUid() > 0) {
						try {
							if (bind.getOpenType() == BindModel.OpenType.SINA
									.getMark()) {
								friends = shareService.loadSinaFollows(
										bind.getOpenUid(),
										bind.getAccessToken());
							}

							if (bind.getOpenType() == BindModel.OpenType.TENCENT
									.getMark()) {
								friends = shareService.loadQQFollows(
										bind.getOpenUid(),
										bind.getAccessToken());
							}
						} catch (Exception e) {
							logger.error("获取第三方好友信息出错！Params=" + bind
									+ ";Error Message=" + e);
						}
					}
					if (friends != null && friends.size() > 0) {
						try {
							// 插入第三方关系表信息
							clientUserService.insertOpenFriend(user.getUid(),
									friends, bind.getOpenType());
						} catch (Exception e) {
							logger.error("插入第三方好友信息入库出错！;Error Message=" + e);
						}
					}

					if (friends != null && friends.size() > 0) {
						try {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("p_exectype", 1);// 表示程序调用执行
							map.put("p_functype", 1);// 表示匹配平台类型
													 // type=0，获取全部匹配的用户信息，type=1
													 // 第三方平台，type=2手机通讯录
							map.put("p_uid", user.getUid());// 用户ID
							clientUserService.updateThfriend(map);
							// 1、取出最新匹配成功数据
							List<OpenFriend> matchedFriends = clientUserService
									.queryMatchedOpenFriends(user.getUid());
							// 2、插入缓存
							redisRelationshipService.insertMatchedFriends(
									user.getUid(), matchedFriends);
							// 3、更新数据库状态
							clientUserService.updateOpenFriend(user.getUid());
							// 4、TODO 插入搜索引擎
							// this.buildOpenUserIndex(uid,friends);
						} catch (Exception e) {
							logger.error("第三方好友匹配出错！;Error Message=" + e);
						}
					}

					// 自动发私信
					try {
						MessageModel model = new MessageModel();
						model.setCreateTime(System.currentTimeMillis());
						model.setIsDelete(0);
						model.setIsNew(1);
						model.setMessageText("你好，欢迎来到趣拍！让趣拍君陪着你一起记录生活，认识新盆友吧！PS：趣拍君不是机器人，有什么不懂的，都可以私信我哟！≥ω≤");
						model.setRecUid(user.getUid());
						model.setUid(QUPAI_UID);
						// 插入私信详细信息
						model = redisMessageService.insertMessage(model);
						// 插入对话列表
						redisMessageService.insertDialog(QUPAI_UID,
								user.getUid());
						// 插入对话详细列表
						RedisMessageModel redisModel = model.asRedisForm();
						redisMessageService.insertDialogMessage(redisModel);

						// 插入数据库 自动发的私信不写入数据库了
						// model.setMessageText(EmojiUtils.encodeEmoji(model.getMessageText()));
						// clientMessageService.insertUserMessage(model);

					} catch (Exception e) {
						logger.error("私信发送失败!Error Message=" + e);
					}

					// 创建索引
					// indexBuilder.buildUserSuggestIndex(user.getNickName());
					// indexBuilder.buildUserIndex(user.getNickName(),
					// user.getUid());
					try {
						IndexUserModel indexModel = new IndexUserModel();
						indexModel.setNickName(user.getNickName());
						indexModel.setSignature(user.getSignature());
						indexModel.setTime(user.getCreateTime());
						indexModel.setUid(user.getUid());
						indexBuilder.buildUserIndex(indexModel);
					} catch (Exception e) {
						logger.error("创建用户索引出错！Error Message" + e);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Error");
		}

	}
}
