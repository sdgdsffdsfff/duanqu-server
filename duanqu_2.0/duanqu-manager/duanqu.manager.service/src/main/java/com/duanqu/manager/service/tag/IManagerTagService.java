package com.duanqu.manager.service.tag;

import java.util.Map;

import com.duanqu.common.model.OtherTagModel;
import com.duanqu.common.model.TagModel;
import com.duanqu.common.submit.MTagSubmit;

public interface IManagerTagService {
	void insertOtherTag(OtherTagModel otherTagModel);
	OtherTagModel selectOtherTagModel(int tagType);
	
	/**
	 * @param tagModel
	 * @return
	 * 获取标签实体
	 */
	TagModel getTagModel(TagModel tagModel);
	
	
	/**
	 * @param tagModel
	 * 添加修改标签
	 */
	Map<String, Object> updateTagModel(TagModel tagModel,long cid);
	
	/**
	 * @param mTagSubmit
	 * 查询标签列表
	 */
	void queryTagList(MTagSubmit mTagSubmit);
	
	/**
	 * @param mTagSubmit
	 * 查询编辑推荐的热门标签
	 */
	void queryTagHotList(MTagSubmit mTagSubmit);
	
	void queryTagHotImageList(MTagSubmit mTagSubmit);
	
	
	/**
	 * @param tid
	 * 添加热门推送
	 */
	void insertTagHot(String tid); 
	
	/**
	 * @param tid
	 * 取消推送
	 */
	void deleteTagHot(MTagSubmit mTagSubmit);
	
	void insertTagHotImage(MTagSubmit mTagSubmit) throws Exception;
	
	void insertRedis(MTagSubmit mTagSubmit);

}
