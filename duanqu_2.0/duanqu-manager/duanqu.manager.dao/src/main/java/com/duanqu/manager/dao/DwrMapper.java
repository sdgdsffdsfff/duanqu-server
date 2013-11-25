package com.duanqu.manager.dao;

import java.util.List;
import java.util.Map;

import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.LikeContentModel;
import com.duanqu.common.model.RecommendModel;
import com.duanqu.common.model.UserModel;

public interface DwrMapper {
	
	/**
	 * @param cid
	 * @return
	 * 根据内容id获取内容的私密性和状态
	 */
	ContentModel getContentModelByCid(RecommendModel recommendModel);
	
	/**
	 * @param likeContentModel
	 */
	void insertLikeContentModel(LikeContentModel likeContentModel);//插入喜欢表
	/**
	 * @param map
	 */
	void updateContentLikeNum(Map<String, Object> map);//更新评论数
	
	List<Long> queryUserListByLikeMj(long cid);
	
	/**
	 * @param uid
	 * @return
	 * 判断用户是不是被推荐
	 */
	UserModel getUserModel(Map<String,Object> map);
	
	
	
	

}
