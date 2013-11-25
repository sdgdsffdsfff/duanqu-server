package com.duanqu.client.service.info;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.duanqu.client.dao.ClientContentMapper;
import com.duanqu.common.DuanquStringUtils;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.ContentTagModel;
import com.duanqu.common.model.LogShareModel;
import com.duanqu.common.model.LogShowModel;
import com.duanqu.common.model.SubjectContentModel;
import com.duanqu.common.model.TagModel;
import com.duanqu.redis.service.content.IRedisContentService;

public class ClientContentServiceImpl implements IClientContentService {

	ClientContentMapper clientContentMapper;
	
	private IRedisContentService redisContentService;

	public ClientContentMapper getClientContentMapper() {
		return clientContentMapper;
	}

	public void setClientContentMapper(ClientContentMapper clientContentMapper) {
		this.clientContentMapper = clientContentMapper;
	}
	
	

	@Override
	public void updateContentStatusToDelete(long cid) {
		ContentModel contentModel=clientContentMapper.getContentRecommend(cid);
		if(contentModel!=null){
			clientContentMapper.deleteRecommendModel(cid);
			redisContentService.deleteContentFindList(cid);
		}
		clientContentMapper.updateContent(cid);
	}
	
	

	@Override
	public ContentModel getContentModel(long cid) {
		return clientContentMapper.getContentModel(cid);
	}

	@Override
	public void updateContentMd5(ContentModel contentModel) {
		clientContentMapper.updateContentMd5(contentModel);
	}
	

	@Override
	public void updateContentShowTimes(long cid, int st) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("st", st);
		clientContentMapper.updateContentShowTimes(map);
	}
	

	@Override
	public void updateContentSinaShareNum(long cid, int num) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("num", num);
		clientContentMapper.updateContentSinaShareNum(map);
	}

	@Override
	public void updateContentQuanShareNum(long cid, int num) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("num", num);
		clientContentMapper.updateContentQuanShareNum(map);	
	}

	@Override
	public void insertContentInfo(ContentModel contentModel) {
		clientContentMapper.insertContentInfo(contentModel);
		ContentTagModel contentTagModel = new ContentTagModel();
		contentTagModel.setCid(contentModel.getCid());
		String description = contentModel.getDescription() ;
		String substr = "";
		Set<String> set = DuanquStringUtils.getTags(description);
		for (Iterator<String> iterator = set.iterator(); iterator
				.hasNext();) {
			substr = iterator.next().replaceAll("#", "").trim();
			TagModel tagModel = clientContentMapper.selectTagInfo(substr);
			if (tagModel == null) {
				TagModel tag = new TagModel();
				tag.setTagText(substr);
				clientContentMapper.insertTagInfo(tag);
				contentTagModel.setTid(tag.getTid());
			} else {
				contentTagModel.setTid(tagModel.getTid());
			}
			clientContentMapper.insertContentTag(contentTagModel);
		}
		if(contentModel.getActiveId()!=0){
			SubjectContentModel subjectContentModel=new SubjectContentModel();
			subjectContentModel.setSid(contentModel.getActiveId());
			subjectContentModel.setCid(contentModel.getCid());
			clientContentMapper.insertSubjectContent(subjectContentModel);
		}
		
	}

	@Override
	public void insertShareContent(LogShareModel logShareModel) {
		clientContentMapper.insertShareContent(logShareModel);
		
	}

	@Override
	public void insertShowContent(LogShowModel logShowModel) {
		clientContentMapper.insertShowContent(logShowModel);
		
	}

	public void setRedisContentService(IRedisContentService redisContentService) {
		this.redisContentService = redisContentService;
	}
	
	
	
}
