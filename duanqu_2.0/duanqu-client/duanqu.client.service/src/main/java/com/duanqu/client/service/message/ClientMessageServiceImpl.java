package com.duanqu.client.service.message;

import com.duanqu.client.dao.ClientMessageMapper;
import com.duanqu.common.model.MessageModel;

public class ClientMessageServiceImpl implements IClientMessageService {

	private ClientMessageMapper clientMessageMapper;
	
	@Override
	public void insertUserMessage(MessageModel messageModel) {
		long uid=messageModel.getUid();
		long rec_uid=messageModel.getRecUid();
		String type="";
		if(uid<rec_uid){
			type=uid+"|"+rec_uid;
		}else if(uid>rec_uid){
			type=rec_uid+"|"+uid;
		}
		messageModel.setType(type);
		clientMessageMapper.insertUserMessage(messageModel);

	}
	
	

	@Override
	public void deleteUserMessage(long uid, int type) {
		if(type==0){
			clientMessageMapper.deleteSendUserMessage(uid);
		}else{
			clientMessageMapper.deleteReceiveUserMessage(uid);
		}
		
	}



	public ClientMessageMapper getClientMessageMapper() {
		return clientMessageMapper;
	}

	public void setClientMessageMapper(ClientMessageMapper clientMessageMapper) {
		this.clientMessageMapper = clientMessageMapper;
	}
	

}
