package com.duanqu.client.service.info;

import java.util.Map;

import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.LogShareModel;
import com.duanqu.common.model.LogShowModel;

public interface IClientContentService {
	void insertContentInfo(ContentModel contentModel);//插入内容基本信息表
	void updateContentStatusToDelete(long cid);//用户删除
	/**
	 * @param contentModel
	 * 更新内容的md5值
	 */
	void updateContentMd5(ContentModel contentModel);
	
	/**
	 * @param cid
	 * @param st
	 * 更新播放次数
	 */
	void updateContentShowTimes(long cid,int st);
	
	ContentModel getContentModel(long cid);
	
	/**
	 * @param map
	 * 更新新浪分享次数
	 */
	void updateContentSinaShareNum(long cid,int num);
	
	/**
	 * @param map
	 * 更新朋友圈分享次数
	 */
	void updateContentQuanShareNum(long cid,int num);
	
	/**
	 * @param logShareModel
	 * 添加分享日志
	 */
	void insertShareContent(LogShareModel logShareModel);
	
	/**
	 * @param logShowModel
	 * 添加播放日志
	 */
	void insertShowContent(LogShowModel logShowModel);
	
	
	
	
}
