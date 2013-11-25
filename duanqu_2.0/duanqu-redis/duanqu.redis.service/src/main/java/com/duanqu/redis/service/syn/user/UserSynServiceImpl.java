package com.duanqu.redis.service.syn.user;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import com.duanqu.common.DuanquSecurity;
import com.duanqu.common.index.IIndexBuilder;
import com.duanqu.common.model.IndexUserModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.service.user.IRedisRelationshipService;
import com.duanqu.redis.service.user.IRedisUserService;
import com.duanqu.redis.utils.key.TimelineKeyManager;

public class UserSynServiceImpl extends BaseRedisService implements
		IUserSynService {
	IRedisRelationshipService redisRelationshipService;
	IRedisUserService redisUserService;
	IIndexBuilder indexBuilder;
	
	@Override
	public boolean synUserAdd(UserModel user) {
		if (user != null){
			user.setToken(DuanquSecurity.encodeToken(user.getEmail()));
			redisUserService.insertUser(user);
			// 创建索引
			IndexUserModel indexModel = new IndexUserModel();
			indexModel.setNickName(user.getNickName());
			indexModel.setSignature(user.getSignature());
			indexModel.setTime(user.getCreateTime());
			indexModel.setUid(user.getUid());
			indexBuilder.buildUserIndex(indexModel);
			return true;
		}
		return false;
	}

	@Override
	public boolean synUserForbid(long uid) {
		UserModel user = redisUserService.getUser(uid);
		if (user != null && user.getUid() > 0){
			redisUserService.updateUserStatus(uid, 0);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean synUserUnforbid(long uid) {
		UserModel user = redisUserService.getUser(uid);
		if (user != null && user.getUid() > 0){
			redisUserService.updateUserStatus(uid, 1);
			return true;
		}
		return false;
	}

	@SuppressWarnings({  "unchecked", "rawtypes" })
	@Override
	public boolean synFollow(long uid,long fid) {
		UserModel user = redisUserService.getUser(uid);
		UserModel friend = redisUserService.getUser(fid);
		
		if (user != null && user.getUid() > 0 && friend != null
				&& friend.getUid() > 0) {
			// 添加关注关系
			redisRelationshipService.follow(uid, fid);
			//TODO 马甲帐号进行假关注其实可以不用分发，待讨论
			if (!redisUserService.isFamous(fid)) {
				// 推送发布的内容
				Set createSet = relationTemplate.boundZSetOps(TimelineKeyManager.getUserPublicList(fid)).rangeWithScores(0, -1);
				// 推送转发的
				Set forwardSet = relationTemplate.boundZSetOps(TimelineKeyManager.getUserForwardList(fid)).rangeWithScores(0, -1);
				Set pushSet = new HashSet();
				pushSet.addAll(createSet);
				pushSet.addAll(forwardSet);
				Iterator it = pushSet.iterator();
				while (it.hasNext()) {
					TypedTuple obj = (TypedTuple) it.next();
					relationTemplate.boundZSetOps(TimelineKeyManager.getPublicTimelineKey(uid)).add((String) obj.getValue(),
							obj.getScore().longValue());
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean sysUbFollow(long uid,long fid) {
		return false;
	}
	
	@Override
	public boolean sysTalentInfo(long uid, int isTalent, String talentDesc) {
		try{
			redisUserService.updateUserTalentInfo(uid, isTalent, talentDesc);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	public void setRedisUserService(IRedisUserService redisUserService) {
		this.redisUserService = redisUserService;
	}

	public void setRedisRelationshipService(
			IRedisRelationshipService redisRelationshipService) {
		this.redisRelationshipService = redisRelationshipService;
	}

	public void setIndexBuilder(IIndexBuilder indexBuilder) {
		this.indexBuilder = indexBuilder;
	}

}
