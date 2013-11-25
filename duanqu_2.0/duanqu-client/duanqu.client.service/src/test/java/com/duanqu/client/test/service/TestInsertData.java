package com.duanqu.client.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.duanqu.client.service.comment.BaseTestServiceImpl;
import com.duanqu.common.model.OpenFriend;
import com.duanqu.redis.service.user.IRedisRelationshipService;

public class TestInsertData  extends BaseTestServiceImpl {
	@Resource
	IRedisRelationshipService redisRelationshipService;
	@Test
	public void insertNoMatchedUser(){
		List<OpenFriend> openFriends = new ArrayList<OpenFriend>();
		int openType = 2;
		for (int i = 0;i<50;i++){
			OpenFriend friend = new OpenFriend();
			friend.setOpenNickName("腾讯测试"+i);
			friend.setOpenType(openType);
			friend.setOpenUserId("test"+i);
			friend.setOpenUserName("username"+i);
			openFriends.add(friend);
		}
		redisRelationshipService.insertNoMatchFriends(1, openFriends, openType);
	}
}
