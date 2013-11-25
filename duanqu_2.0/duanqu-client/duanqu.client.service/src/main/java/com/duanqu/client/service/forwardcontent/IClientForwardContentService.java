package com.duanqu.client.service.forwardcontent;


import com.duanqu.common.model.ForwardContentModel;

public interface IClientForwardContentService {
	void insertForwardContentModel(ForwardContentModel forwardContentModel);//增加转发
	void deleteForwardContentModel(ForwardContentModel forwardContentModel);//取消转发

}
