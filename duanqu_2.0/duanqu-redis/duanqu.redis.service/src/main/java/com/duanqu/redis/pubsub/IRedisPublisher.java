package com.duanqu.redis.pubsub;

import com.duanqu.common.NoticeMessage;


public interface IRedisPublisher {
	
	
	/**
	 * 发送消息
	 * @param message
	 */
	public void publish(NoticeMessage msg);
}
