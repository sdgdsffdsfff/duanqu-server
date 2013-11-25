package com.duanqu.manager.service.dwr;

import java.util.Map;

public interface IManagerDwrService {
	
	
	/**
	 * @param cid
	 * @return
	 * 推送时检查选中的内容中是否包含私有内容
	 */
	Map<String, Object> checkContentIsPrivate(String cid,int type);
	
	/**
	 * @param cid
	 * @return
	 * 后台使用马甲增加喜欢数
	 */
	String insertLikeContent(long cid,int addNum);
	
	/**
	 * @param uid
	 * @return
	 * 查询用户是不是被推荐
	 */
	boolean insertUserIsRecommend(String uid,int type);
	
	/**
	 * @param uid
	 * @param type
	 * @param reason
	 * 好友推荐公共账号推荐
	 */
	void insertRecommended(String uid,int type,String reason);

}
