package com.duanqu.client.dao;

import com.duanqu.common.model.MessageModel;

public interface ClientMessageMapper {
	void insertUserMessage(MessageModel messageModel);
	void deleteSendUserMessage(long sendUid);//发送人删除消息
	void deleteReceiveUserMessage(long recUid);//接受人删除消息

}
