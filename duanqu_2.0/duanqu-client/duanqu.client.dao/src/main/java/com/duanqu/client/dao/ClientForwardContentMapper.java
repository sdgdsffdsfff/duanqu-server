package com.duanqu.client.dao;

import java.util.Map;

import com.duanqu.common.model.ForwardContentModel;

public interface ClientForwardContentMapper {
	
	void insertForwardContentModel(ForwardContentModel forwardContentModel );//插入转发表
	void updateContentForwardNum(Map<String, Object> map);//更新转发数
	void deleteForwardContentModel(ForwardContentModel forwardContentModel );//取消转发

}
