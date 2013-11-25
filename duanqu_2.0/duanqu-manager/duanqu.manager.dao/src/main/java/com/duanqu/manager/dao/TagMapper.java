package com.duanqu.manager.dao;

import java.util.List;
import java.util.Map;

import com.duanqu.common.model.ContentTagModel;
import com.duanqu.common.model.OtherTagModel;
import com.duanqu.common.model.TagHotModel;
import com.duanqu.common.model.TagModel;
import com.duanqu.common.submit.MTagSubmit;

public interface TagMapper {

	void insertOtherTag(OtherTagModel otherTagModel);
	void updateOtherTag(OtherTagModel otherTagModel);
	OtherTagModel selectOtherTagModel(int tagType);
	
	/**
	 * @param mTagSubmit
	 * @return
	 * 查询标签列表
	 */
	List<Map<String, Object>> queryTagList(MTagSubmit mTagSubmit);
	
	/**
	 * @param mTagSubmit
	 * @return
	 * 查询标签总数
	 */
	long queryTagListCount(MTagSubmit mTagSubmit);
	
	/**
	 * @param mTagSubmit
	 * @return
	 * 查询编辑推荐的标签
	 */
	List<Map<String, Object>> queryTagHotList(MTagSubmit mTagSubmit);
	
	/**
	 * @param mTagSubmit
	 * @return查询编辑推荐标签总数
	 */
	long queryTagHotListCount(MTagSubmit mTagSubmit);
	
	/**
	 * @param tagModel
	 * @return
	 * 查询标签信息
	 */
	TagModel getTagModel(TagModel tagModel);
	
	/**
	 * @param tagModel
	 * 添加标签
	 */
	void insertTagModel(TagModel tagModel);
	
	/**
	 * @param tagModel
	 * 修改标签
	 */
	void updateTagModel(TagModel tagModel);
	
	/**
	 * @param contentTagModel
	 * 添加标签内容关系表
	 */
	void insertContentTag(ContentTagModel contentTagModel);
	
	/**
	 * @param contentTagModel
	 * 删除标签内容关系表
	 */
	void deleteContentTag(ContentTagModel contentTagModel);
	
	/**
	 * @param map
	 * 添加热门推荐
	 */
	void insertTagHot(TagHotModel tagHotModel);
	
	/**
	 * @param tid
	 * 删除热门推荐
	 */
	void deleteTagHot(TagHotModel tagHotModel);
	
	/**
	 * @param tid
	 * @return
	 * 获取热门推荐
	 */
	TagHotModel getTagHot(long tid);
	
	/**
	 * @param tagHotModel
	 * 修改热门推送时间
	 */
	void updateTagHot(TagHotModel tagHotModel);
	
	List<Map<String, Object>> queryEditorTagList();
	
	/**
	 * @param tagHotModel
	 * 插入推荐图片标签
	 */
	void insertTagHotImage(TagHotModel tagHotModel);
	
	/**
	 * @param mTagSubmit
	 * @return
	 * 
	 */
	List<Map<String, Object>> queryTagHotImageList(MTagSubmit mTagSubmit);
	
	/**
	 * @param mTagSubmit
	 * @return
	 */
	long queryTagHotImageListCount(MTagSubmit mTagSubmit);
	
	void updateTagOrderNum(TagHotModel tagHotModel);
	
	List<TagHotModel> queryTagHotModels();
	
}
