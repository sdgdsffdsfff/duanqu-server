package com.duanqu.manager.service.content;

import java.util.List;
import java.util.Map;

import com.duanqu.common.model.CommentModel;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.SetContentModel;
import com.duanqu.common.submit.MContentSubmit;
import com.duanqu.manager.submit.ManagerContentSubmit;




public interface IManagerContentService {
	
	void queryContentForms(MContentSubmit mContentSubmit) throws Exception;//查询内容列表
	void queryTsContentForms(MContentSubmit mContentSubmit) throws Exception;//查询推送内容列表
	void insertRecommendModel(MContentSubmit mContentSubmit) throws Exception;//插入首页编辑推存
	void updateContent(ContentModel contentModel);//删除或者屏蔽
	String insertComment(CommentModel commentModel);//编辑后台评论
	String insertContentInfo(ManagerContentSubmit managerContentSubmit);//编辑后台上传视频
	void deleteRecommendModel(long cid,int type);//删除编辑推送内容
	void insertHotContentList();//插入热门内容
	void insertRedisFromContent();//定时扫描内容表，投放标志是0，且上传时间小于当前时间的
	
	void queryReportList(MContentSubmit mContentSubmit);//查询举报列表
	
	/**
	 * @param jbid
	 * 驳回举报信息
	 */
	void updateReport(long jbid);
	
	/**
	 * @param createTime
	 * 定时推送内容到发现或者首页
	 */
	void insertDsTsContent();
	
	/**
	 * @param mContentSubmit
	 * @throws Exception
	 * 短趣君内容列表
	 */
	void queryContentFormsByDqj(MContentSubmit mContentSubmit) throws Exception;
	
	/**
	 * @param cid
	 * @param st
	 * 更新播放次数
	 */
	void duanquUpdateContentShowTimes(long cid,int st);
	
	ContentModel getContentModel(long cid);
	
	/**
	 * @param contentModel
	 * @return
	 * 修改内容描述信息
	 */
	Map<String, Object> updateContentDescription(ContentModel contentModel);
	
	/**
	 * @param cid
	 * @return
	 * 获取新浪朋友圈的分享数
	 */
	Map<String, Object> getContentSinaQuanNum(long cid);
	
	/**
	 * @param cid
	 * @param type
	 * @param num
	 * 更新新浪朋友圈的分享数
	 */
	void updateContentSinaQuanNum(long cid,int type,int num);
	
	/**
	 * @param cid
	 * @return
	 * 获取编辑设置的内容排行榜
	 */
	Map<String, Object> getSetContentList(long cid,int type);
	
	void insertSetContent(long cid,int order_num,int type);
	
	List<SetContentModel> queryContentModels();
	
	void queryTsHotContent(MContentSubmit mContentSubmit);
	
	void deleteTsHotContent(long cid,int type);
	
	
	/**
	 * 总榜
	 */
	void allHotTop();
	
	/**
	 * 周榜
	 */
	void weekHotTop();
	
	/**
	 * 获取公共账号
	 * @return
	 */
	List<Map<String, Object>> queryPublishUserList();
	
	String saveForward(long uid,String cid);
	
	

}
