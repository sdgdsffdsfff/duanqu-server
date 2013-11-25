package com.duanqu.redis.service.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duanqu.common.model.UserModel;
import com.duanqu.redis.service.user.IRedisUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis-test-applicationContext.xml")
public class RedisUserServiceTest {
	
	@Resource
	IRedisUserService redisUserService;
	
	@Test
	public void loadAllUser() {
		List<UserModel> users = redisUserService.loadAllUser();
		for (UserModel user: users){
			if (user.getAvatarUrl().indexOf("http://")>=0){
				user.setAvatarUrl(user.getAvatarUrl().replace("default.jpg", "/default.jpg"));
				redisUserService.update(user);
			}
			System.out.println(user);
		}
	}

}
