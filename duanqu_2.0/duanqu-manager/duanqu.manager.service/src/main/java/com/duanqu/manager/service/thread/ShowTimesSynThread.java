package com.duanqu.manager.service.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.duanqu.common.model.ContentModel;
import com.duanqu.manager.service.content.IManagerContentService;
import com.duanqu.redis.service.content.IRedisContentService;

public class ShowTimesSynThread implements Runnable  {
	Log logger = LogFactory.getLog(ShowTimesSynThread.class);
	IRedisContentService redisContentService;
	IManagerContentService managerContentService;
	public void run() {
		while(true){
			try{
				long cid = redisContentService.getCidFromQueue();
				if (cid > 0){
					ContentModel content = redisContentService.getContent(cid);
					if (content != null && content.getCid() > 0){
						int showTimes = content.getShowTimes() == 0?1:content.getShowTimes();
						try{
							managerContentService.duanquUpdateContentShowTimes(cid, showTimes);
							//logger.error("++++同步播放次数成功！");
						}catch (Exception e) {
							logger.error("异步线程，更新播放次数出错！Message="+e.getMessage()+",e="+e);
						}
					}
				}
				try{
					Thread.sleep(50);
				}catch(Exception e){
					logger.error("休眠失败！"+e);
				}
			}catch(Exception e){
				logger.error(e);
			}
		}
	}
	
	public void setRedisContentService(IRedisContentService redisContentService) {
		this.redisContentService = redisContentService;
	}
	public void setManagerContentService(
			IManagerContentService managerContentService) {
		this.managerContentService = managerContentService;
	}

}
