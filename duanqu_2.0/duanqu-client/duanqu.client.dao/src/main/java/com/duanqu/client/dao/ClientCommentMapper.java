package com.duanqu.client.dao;

import java.util.Map;

import com.duanqu.common.model.CommentModel;
public interface ClientCommentMapper {
	void insertContentComment(CommentModel commentModel);//插入评论表
	int deleteContentComment(CommentModel commentModel);//删除子评论
	int deleteAllContentComment(CommentModel commentModel);//删除主评论
	void updateContentInfo(Map<String,Object> map);//更新评论数
	/**
	 * @param 
	 * @return
	 * 获取评论实体
	 */
	CommentModel getCommentModel(CommentModel commentModel);
}
