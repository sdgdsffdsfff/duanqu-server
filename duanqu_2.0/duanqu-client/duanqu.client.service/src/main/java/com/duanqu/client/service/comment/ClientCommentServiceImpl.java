package com.duanqu.client.service.comment;

import java.util.HashMap;
import java.util.Map;

import com.duanqu.client.dao.ClientCommentMapper;
import com.duanqu.common.model.CommentModel;

public class ClientCommentServiceImpl implements IClientCommentService {

	
	ClientCommentMapper clientCommentMapper;
	
	
	public ClientCommentMapper getClientCommentMapper() {
		return clientCommentMapper;
	}

	public void setClientCommentMapper(ClientCommentMapper clientCommentMapper) {
		this.clientCommentMapper = clientCommentMapper;
	}

	@Override
	public void insertContentComment(CommentModel commentModel) {
		long cid=commentModel.getCid();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("num", -1);
		clientCommentMapper.insertContentComment(commentModel);
		clientCommentMapper.updateContentInfo(map);

	}

	@Override
	public void deleteContentComment(CommentModel commentModel) {
		CommentModel cm=clientCommentMapper.getCommentModel(commentModel);
		Map<String,Object> map=new HashMap<String, Object>();
		if(cm!=null){
		long cid=cm.getCid();
		long rootId=cm.getRootId();
		map.put("cid", cid);
		int num=0;
		if(rootId==0){
			//删除主评论及所有子评论
			num=clientCommentMapper.deleteAllContentComment(commentModel);
		}else {
			num=clientCommentMapper.deleteContentComment(commentModel);
		}
		map.put("num", num);
		clientCommentMapper.updateContentInfo(map);
		}
	}
}
