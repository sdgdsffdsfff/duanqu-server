package com.duanqu.client.service.feedback;

import com.duanqu.client.dao.ClientFeedBackMapper;
import com.duanqu.common.model.FeedBackModel;

public class ClientFeedBackServiceImpl implements IClientFeedBackService {

	
	ClientFeedBackMapper clientFeedBackMapper;
	
	@Override
	public void insertUserFeedBackModel(FeedBackModel feedBackModel) {
		clientFeedBackMapper.insertUserFeedBackModel(feedBackModel);
	}
	

	public ClientFeedBackMapper getClientFeedBackMapper() {
		return clientFeedBackMapper;
	}

	public void setClientFeedBackMapper(ClientFeedBackMapper clientFeedBackMapper) {
		this.clientFeedBackMapper = clientFeedBackMapper;
	}
	
	
	

	


}
