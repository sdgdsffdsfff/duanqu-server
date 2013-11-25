package com.duanqu.redis.service.syn.comment;

import com.duanqu.common.model.CommentModel;

public interface ICommentSynService {
	
	/**
	 * 同步评论
	 * 注意一下几个字段的赋值
	 * replyUid;//如果直接评论内容则 = 0；
	 * parentId;//父评论Id 
	 * rootId;//根评论ID 
	 * @param comment
	 * @return
	 */
	public boolean synCommentAdd(CommentModel comment);
	
	/**
	 * 同步删除评论
	 * @param commentId
	 * @return
	 */
	public boolean synCommentDelete(long commentId);

}
