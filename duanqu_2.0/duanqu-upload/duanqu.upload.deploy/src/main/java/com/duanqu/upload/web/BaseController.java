package com.duanqu.upload.web;

import javax.annotation.Resource;

import com.duanqu.common.model.UserModel;
import com.duanqu.redis.service.user.IRedisUserService;

public abstract class BaseController {

	@Resource
	IRedisUserService redisUserService;

	public UserModel getUser(String token) {
		long uid = this.getUid(token);
		if (uid > 0) {
			UserModel model = redisUserService.getUser(uid);
			if (model != null && model.getUid() > 0) {
				return model;
			}
		}
		return null;
	}
	
	public long getUid(String token) {
		long uid = redisUserService.getUid(token);
		return uid;
	}
	
	
}
