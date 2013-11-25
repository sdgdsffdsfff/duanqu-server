package com.duanqu.redis.pubsub;

import org.springframework.data.redis.listener.ChannelTopic;

import com.alibaba.fastjson.JSON;
import com.duanqu.common.NoticeMessage;
import com.duanqu.redis.service.BaseRedisService;

public class DuanquRedisPublisherImpl extends BaseRedisService implements IRedisPublisher {

	private ChannelTopic topic;
	@Override
	public void publish(NoticeMessage msg) {
		jmsTemplate.convertAndSend(topic.getTopic(), JSON.toJSONString(msg));
	}

	public void setTopic(ChannelTopic topic) {
		this.topic = topic;
	}
}
