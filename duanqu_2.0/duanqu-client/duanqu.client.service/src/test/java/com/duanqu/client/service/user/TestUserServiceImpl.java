package com.duanqu.client.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.duanqu.client.dao.ClientUserMapper;
import com.duanqu.client.service.comment.BaseTestServiceImpl;
import com.duanqu.common.model.BindModel;
import com.duanqu.common.model.FriendModel;
import com.duanqu.common.model.OpenFriend;
import com.duanqu.common.model.UserMobileModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.redis.service.user.IRedisUserService;

public class TestUserServiceImpl extends BaseTestServiceImpl {
	@Resource
	private ClientUserMapper clientUserMapper;

	@Resource
	IRedisUserService redisUserService;
	@Resource
	private IClientUserService userService;

	// @Test
	@Rollback(false)
	public void testInsertRegister() {
		List<UserModel> users = redisUserService.loadAllUser();
		for (UserModel userModel : users) {
			try {
				userService.insertRegister(userModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// clientUserMapper.insertUserInfo(userModel);

		}

	}

	@Rollback(false)
	public void testDuanquLogin() {

		UserModel userModel = new UserModel();
		userModel.setUid(7);
		userModel.setEmail("test6@danqoo.com");
		userModel.setLoginPassward("8");
		userModel.setNickName("dm.yang");
		userModel.setMobile("13023661391");
		userModel.setAvatarUrl("D:\\test");
		userModel.setCreateTime(System.currentTimeMillis());
		userModel.setLongitude(12);
		userModel.setLatitude(23);
		userModel.setLastLoginTime(System.currentTimeMillis());
		userModel.setSignature("测试哈哈");
		userModel.setBackgroundUrl("D:\\test");
		userModel.setRoleId(1);
		userModel.setToken("dsfffffff11111");
		// clientUserMapper.insertUserInfo(userModel);
		userService.updateLogin(userModel);
	}

	@Rollback(false)
	public void testThirdRegister() {

		UserModel userModel = new UserModel();
		userModel.setUid(23);
		userModel.setEmail("test6@danqoo.com");
		userModel.setLoginPassward("981111");
		userModel.setNickName("dm.yang新浪");
		userModel.setMobile("13023661391");
		userModel.setAvatarUrl("D:\\test");
		userModel.setCreateTime(System.currentTimeMillis());
		userModel.setLongitude(12);
		userModel.setLatitude(23);
		userModel.setLastLoginTime(System.currentTimeMillis());
		userModel.setSignature("测试哈哈");
		userModel.setBackgroundUrl("D:\\test");
		userModel.setRoleId(1);

		// clientUserMapper.insertUserInfo(userModel);
		BindModel bindModel = new BindModel();
		bindModel.setUid(23);
		bindModel.setOpenType(2);
		bindModel.setOpenUid("新浪111");
		bindModel.setAccessToken("22222222333");
		bindModel.setCreatetime(System.currentTimeMillis());
		bindModel.setRefreshToken("33333333444");

		userService.insertRegisterTh(userModel, bindModel);
		// userService.duanquLogin(userModel);
	}

	@Rollback(false)
	public void testInsertOpenFriend() {
		OpenFriend openFriend1 = new OpenFriend();
		openFriend1.setUid(5);
		openFriend1.setOpenType(1);

		openFriend1.setOpenUserId("test5555");
		openFriend1.setOpenUserName("test555");
		openFriend1.setOpenNickName("你好");
		OpenFriend openFriend2 = new OpenFriend();
		openFriend2.setUid(7);
		openFriend2.setOpenType(1);
		openFriend2.setOpenUserId("test666");
		openFriend2.setOpenUserName("test666");
		openFriend2.setOpenNickName("你好啊111");
		List<OpenFriend> list = new ArrayList<OpenFriend>();
		list.add(openFriend1);
		list.add(openFriend2);
		userService.insertOpenFriend(2, list,1);

	}

	// @Test
	@Rollback(false)
	public void testInsertMobiles() {
		UserMobileModel userMobileModel = new UserMobileModel();
		userMobileModel.setNickName("ydm");
		userMobileModel.setMobile("11111111");
		userMobileModel.setCrateTime(System.currentTimeMillis());

		UserMobileModel userMobileModel2 = new UserMobileModel();
		userMobileModel2.setNickName("ydm2");
		userMobileModel2.setMobile("111111112");
		userMobileModel2.setCrateTime(System.currentTimeMillis());

		List<UserMobileModel> list = new ArrayList<UserMobileModel>();
		list.add(userMobileModel);
		list.add(userMobileModel2);
		userService.insertMobiles(3, list);

	}

	@Rollback
	public void testUpdateThfriend() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_exectype", 1);
		map.put("p_functype", 2);
		map.put("p_uid", 6);
		userService.updateThfriend(map);

	}

	// @Test

	public void testQueryMathchUserList() {
		List<UserModel> list = userService
				.queryMathchUserList(0, 4, 1368026532);
		for (Iterator<UserModel> iterator = list.iterator(); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
	}

	// @Test
	@Rollback(false)
	public void testInsertFriend() {
		FriendModel friendModel = new FriendModel();
		friendModel.setUid(124);
		friendModel.setFid(123);
		friendModel.setIsFriend(1);
		friendModel.setCreateTime(System.currentTimeMillis());
		userService.insertFriend(friendModel);
	}

	// @Test
	@Rollback(false)
	public void testDeleteFriend() {
		FriendModel friendModel = new FriendModel();
		friendModel.setUid(123);
		friendModel.setFid(124);
		userService.deleteFriend(friendModel);
	}
   // @Test
	public void testGetUserFriendList() {
		//userService.getUserFriendList();

	}
	//@Test
	public void testQuery(){
		OpenFriend openFriend=new OpenFriend();
		openFriend.setUid(3);
		openFriend.setOpenType(1);
		System.out.println(userService.queryOpenFriendListByUid(openFriend).size());
		
	}
	//@Test
	@Rollback(false)
	public void testUpdateUserInfoMobile(){
		userService.updateUserInfoMobile(1, "1111");
	}
	@Test
	@Rollback(false)
	public void testInsertUserModel(){
		for(int i=15;i<100000;i++){
			UserModel userModel=new UserModel();
			userModel.setUid(i);
			userModel.setNickName("测试"+System.currentTimeMillis());
			userModel.setCreateTime(System.currentTimeMillis());
			userModel.setAvatarUrl("20130806/f5c9013e-4cf5-4893-9f1f-02e5d3de804c.jpg");
			userModel.setRoleId(3);
			clientUserMapper.insertUserInfo(userModel);	
		}
	}

}
