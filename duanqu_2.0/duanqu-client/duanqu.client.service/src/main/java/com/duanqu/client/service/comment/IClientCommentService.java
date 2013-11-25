package com.duanqu.client.service.comment;

import com.duanqu.common.model.CommentModel;

public interface IClientCommentService {
	void insertContentComment(CommentModel commentModel);
	void deleteContentComment(CommentModel commentModel);

}
