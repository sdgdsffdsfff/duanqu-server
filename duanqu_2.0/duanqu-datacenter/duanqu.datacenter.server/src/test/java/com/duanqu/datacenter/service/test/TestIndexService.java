package com.duanqu.datacenter.service.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.duanqu.common.index.IESIndexBuilder;
import com.duanqu.common.model.IndexUserModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.redis.service.user.IRedisUserService;

public class TestIndexService extends BaseTest {
	@Resource
	IESIndexBuilder esIndexBuilder;
	@Resource 
	IRedisUserService redisUserService;
	@Test
	public void testIndexUser(){
		List<UserModel> users = redisUserService.loadAllUser();
		for (UserModel user : users){
			IndexUserModel model = new IndexUserModel();
			model.setNickName(user.getNickName());
			model.setSignature(user.getSignature());
			model.setTime(user.getCreateTime());
			model.setUid(user.getUid());
			esIndexBuilder.buildUserIndex(model);
			try{
				Thread.sleep(100);
			}catch (Exception e) {
			}
			System.out.println(user);
		}
	}
	
	//@Test
	public void searchUser(){
		
	}
}
