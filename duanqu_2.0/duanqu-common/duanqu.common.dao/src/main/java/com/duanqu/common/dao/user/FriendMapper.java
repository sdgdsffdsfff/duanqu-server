package com.duanqu.common.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.duanqu.common.model.OpenFriend;
import com.duanqu.common.model.UserMobileModel;

public interface FriendMapper {

	/**
	 * 插入第三方好友信息
	 * 
	 * @param oFriend
	 */
	public void insertOpenFriend(OpenFriend oFriend);

	int queryOpenFriendListCount();

	/**
	 * 分页获取第三方平台好友
	 * 
	 * @param map
	 * @return
	 */
	List<OpenFriend> queryOpenFriendList(@Param("pageStart") int pageStart,
			@Param("pageSize") int pageSize);

	int queryUserMobilesListCount();

	/**
	 * 分页获取通讯录
	 * @param pageStart
	 * @param pageSize
	 * @return
	 */
	List<OpenFriend> queryUserMobilesList(@Param("pageStart") int pageStart,
			@Param("pageSize") int pageSize);

}
