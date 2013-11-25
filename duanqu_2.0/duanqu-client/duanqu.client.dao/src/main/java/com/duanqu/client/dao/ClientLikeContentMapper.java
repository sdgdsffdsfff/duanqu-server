package com.duanqu.client.dao;

import java.util.Map;

import com.duanqu.common.model.LikeContentModel;

public interface ClientLikeContentMapper {
	
	void insertLikeContentModel(LikeContentModel likeContentModel);//插入喜欢表
	void updateContentLikeNum(Map<String, Object> map);//更新评论数
	void deleteLikeContentModel(LikeContentModel likeContentModel);//取消喜欢

}
