package com.duanqu.open;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duanqu.common.dao.user.FriendMapper;
import com.duanqu.common.model.OpenFriend;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
public class OpenUserTest extends AbstractJUnit4SpringContextTests {

	@Resource
	FriendMapper friendMapper;

	@Test
	public void saveTest() {
		/*OpenFriend oFriend = new OpenFriend();
		oFriend.setOpenNickName("蓑笠翁");
		oFriend.setOpenType(1);
		oFriend.setOpenUserId("123123");
		oFriend.setUid(2);
		friendMapper.insertOpenFriend(oFriend);*/
	}
}
