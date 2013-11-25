package com.duanqu.manager.dao;

import java.util.List;
import java.util.Map;

import com.duanqu.common.model.CommentModel;
import com.duanqu.common.submit.MContentSubmit;
import com.duanqu.common.submit.MUserSubmit;
import com.duanqu.manager.submit.ManagerCommentSubmit;

public interface CommentMapper {
	
	void insertContentComment(CommentModel commentModel);//插入评论表
	List<Long> queryUserList(Long cid);//查询可用马甲列表
	void updateContentInfo(Map<String,Object> map);//更新评论数
	void updateContentInfoFalse(Map<String,Object> map);//更新假评论数
	
	/**
	 * @param uid
	 * @return
	 * 查询马甲下面的新评论数
	 */
	List<ManagerCommentSubmit> queryMjCommentSubmits(MUserSubmit mUserSubmit);
	
	long queryMjCommentSubmitsCount(MUserSubmit mUserSubmit);
	
    /**
     * @param mContentSubmit
     * @return
     * 查询短趣君下新评论数
     */
    List<ManagerCommentSubmit> queryDqjCommentSubmits(MContentSubmit mContentSubmit);
	
	/**
	 * @param mContentSubmit
	 * @return
	 * 查询短趣君下新评论的总数
	 */
	long queryDqjCommentSubmitsCount(MContentSubmit mContentSubmit);
	
	ManagerCommentSubmit getManagerCommentSubmit(long id);
	
	int deleteContentComment(CommentModel commentModel);//删除子评论
	int deleteAllContentComment(CommentModel commentModel);//删除主评论
	
	/**
	 * @param id
	 * 更新单条评论为旧评论
	 */
	void updateCommentIsNew(long id);
	

}
