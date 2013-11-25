package com.duanqu.client.service.forwardcontent;

import java.util.HashMap;
import java.util.Map;

import com.duanqu.client.dao.ClientForwardContentMapper;
import com.duanqu.common.model.ForwardContentModel;

public class ClientForwardContentServiceImpl implements IClientForwardContentService {

	private ClientForwardContentMapper clientForwardContentMapper;
	
	@Override
	public void insertForwardContentModel(ForwardContentModel forwardContentModel) {
		clientForwardContentMapper.insertForwardContentModel(forwardContentModel);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cid", forwardContentModel.getCid());
		map.put("num", -1);//转发数+1 这里数据库是更新的是-减，所以这里传了一个负数
		clientForwardContentMapper.updateContentForwardNum(map);

	}

	@Override
	public void deleteForwardContentModel(ForwardContentModel forwardContentModel) {
		clientForwardContentMapper.deleteForwardContentModel(forwardContentModel);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cid", forwardContentModel.getCid());
		map.put("num", 1);//转发数-1
		clientForwardContentMapper.updateContentForwardNum(map);

	}

	public ClientForwardContentMapper getClientForwardContentMapper() {
		return clientForwardContentMapper;
	}

	public void setClientForwardContentMapper(
			ClientForwardContentMapper clientForwardContentMapper) {
		this.clientForwardContentMapper = clientForwardContentMapper;
	}
	
	

}
