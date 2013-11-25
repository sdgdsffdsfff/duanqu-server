package com.duanqu.common.service;


public interface ProducerService {
	/**
	 * 发送用户注册消息
	 */
	public void sendMessage(Object obj);

}
