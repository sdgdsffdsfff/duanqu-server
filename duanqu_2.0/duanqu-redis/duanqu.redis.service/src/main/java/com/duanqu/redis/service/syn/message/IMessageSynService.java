package com.duanqu.redis.service.syn.message;

import java.util.List;

import javapns.notification.PayloadPerDevice;

import com.duanqu.common.model.MessageModel;

public interface IMessageSynService {
	
	/**
	 * 同步私信发送和回复
	 * @param model
	 * @return
	 */
	public boolean synMessageSend(MessageModel message);
	
	/**
	 * 批量发送私信
	 * @param uid
	 * @param content
	 */
	public void batchSynMessageSend(MessageModel message);
	
	
	public List<PayloadPerDevice> toList(List<Long> list);
	
	public boolean synMessageSendBatchs(MessageModel message) ;
	
	public PayloadPerDevice praseMessage(long uid,String message,String innerParam,String type);
	

}
