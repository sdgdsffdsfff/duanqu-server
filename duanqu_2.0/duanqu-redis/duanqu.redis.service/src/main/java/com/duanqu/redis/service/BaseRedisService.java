package com.duanqu.redis.service;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

public class BaseRedisService {
	/**
	 * 内容ContentTemplate
	 */
	protected RedisTemplate contentTemplate;

	/**
	 * 用户ContentTemplate
	 */
	protected RedisTemplate userTemplate;
	
	/**
	 * 关系Template
	 */
	protected RedisTemplate relationTemplate;
	
	/**
	 * 搜索template
	 */
	protected RedisTemplate reportTemplate;
	
	/**
	 * 消息
	 */
	protected RedisTemplate jmsTemplate;
	
	/**
	 * 用户关系数据
	 */
	protected RedisTemplate userRelationTemplate;
	/**
	 * 评论数据
	 */
	protected RedisTemplate commentTemplate;
	
	/**
	 * 消息私信数据
	 */
	protected RedisTemplate messageTemplate;
	
	/**
	 * 第三方平台用户信息
	 */
	protected RedisTemplate openUserTemplate;
	
	
	
	public void setContentTemplate(RedisTemplate contentTemplate) {
		this.contentTemplate = contentTemplate;
	}

	public void setUserTemplate(RedisTemplate userTemplate) {
		this.userTemplate = userTemplate;
	}

	public void setRelationTemplate(RedisTemplate relationTemplate) {
		this.relationTemplate = relationTemplate;
	}

	public RedisTemplate getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(RedisTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public void setJmsTemplate(RedisTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setUserRelationTemplate(RedisTemplate userRelationTemplate) {
		this.userRelationTemplate = userRelationTemplate;
	}
	
	public void setCommentTemplate(RedisTemplate commentTemplate) {
		this.commentTemplate = commentTemplate;
	}
	public void setMessageTemplate(RedisTemplate messageTemplate) {
		this.messageTemplate = messageTemplate;
	}
	public DefaultStringRedisConnection getDefaultStringRedisConnection(){
		return new DefaultStringRedisConnection(relationTemplate.getConnectionFactory().getConnection());
	}

	public void setOpenUserTemplate(RedisTemplate openUserTemplate) {
		this.openUserTemplate = openUserTemplate;
	}


}
