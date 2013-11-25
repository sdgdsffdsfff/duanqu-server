package com.duanqu.client.service.likecontent;


import com.duanqu.common.model.LikeContentModel;

public interface IClientLikeContentService {
	void insertLikeContentModel(LikeContentModel likeContentModel);//增加喜欢
	void deleteLikeContentModel(LikeContentModel likeContentModel);//取消喜欢

}
