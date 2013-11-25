package com.duanqu.manager.service.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.duanqu.manager.dao.UserAdminMapper;
import com.duanqu.redis.service.user.IRedisRelationshipService;

public class RelationshipSynThread implements Runnable  {
	Log logger = LogFactory.getLog(RelationshipSynThread.class);
	IRedisRelationshipService redisRelationshipService;
	UserAdminMapper userAdminMapper;
	@Override
	public void run() {
		while(true){
			try{
				long uid = redisRelationshipService.getUpdateUserIdFromQueue();
				if (uid > 0){
					int fansCount = redisRelationshipService.countFans(uid);
					int followsCount = redisRelationshipService.countFollows(uid);
					int friendsCount = redisRelationshipService.countFriends(uid);
					try{
						userAdminMapper.updateUserRelationshipCount(uid, fansCount, followsCount, friendsCount);
						//logger.error("++++同步好友数，粉丝数，关注数成功！");
					}catch (Exception e) {
						logger.error("异步线程，更新好友数，粉丝数，关注数出错！Message="+e.getMessage()+",e="+e);
					}
				}
				try{
					Thread.sleep(300);
				}catch(Exception e){
					logger.error("休眠失败！"+e);
				}
			}catch(Exception e){
				logger.error(e);
			}
		}
		
	}

	public void setRedisRelationshipService(
			IRedisRelationshipService redisRelationshipService) {
		this.redisRelationshipService = redisRelationshipService;
	}

	public void setUserAdminMapper(UserAdminMapper userAdminMapper) {
		this.userAdminMapper = userAdminMapper;
	}

}
