package com.duanqu.manager.service.tag;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.dao.content.ContentMapper;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.ContentTagModel;
import com.duanqu.common.model.OtherTagModel;
import com.duanqu.common.model.TagHotModel;
import com.duanqu.common.model.TagModel;
import com.duanqu.common.submit.MTagSubmit;
import com.duanqu.manager.dao.TagMapper;
import com.duanqu.redis.service.hot.IRedisHotService;
import com.duanqu.redis.service.syn.system.ISystemSynService;

public class ManagerTagServiceImpl implements IManagerTagService {

	IRedisHotService redisHotService;
	TagMapper tagMapper;
	ContentMapper contentMapper;
	ISystemSynService systemSynService;

	@Override
	public void insertOtherTag(OtherTagModel otherTagModel) {
		int tagType = otherTagModel.getTagType();
		OtherTagModel otm = tagMapper.selectOtherTagModel(tagType);
		if (otm == null) {
			tagMapper.insertOtherTag(otherTagModel);
		} else {
			tagMapper.updateOtherTag(otherTagModel);
		}
		if (tagType == 1) {
			redisHotService.insertPublishTag(otherTagModel.getTagText());
		} else if(tagType==2){
			redisHotService.insertSearchTag(otherTagModel.getTagText());
		}else{
			systemSynService.synNewVersionTips(otherTagModel.getTagText());
		}

	}

	@Override
	public void insertTagHot(String tid) {
		if (tid.endsWith(",")) {
			tid = tid.substring(0, tid.length() - 1);
		}
		String[] tidArray = tid.split(",");
		for (int i = 0; i < tidArray.length; i++) {
			long tidL = Long.parseLong(tidArray[i]);
			TagHotModel tagHotModel = tagMapper.getTagHot(tidL);
			TagHotModel thm = new TagHotModel();
			thm.setTid(tidL);
			thm.setTagType(1);
			thm.setCreateTime(System.currentTimeMillis());
			if (tagHotModel == null) {
				tagMapper.insertTagHot(thm);
			} else {
				tagMapper.updateTagHot(thm);
			}
		}
	}

	@Override
	public void deleteTagHot(MTagSubmit mTagSubmit) {
		TagHotModel tagHotModel=new TagHotModel();
		tagHotModel.setTagType(mTagSubmit.getType());
		tagHotModel.setTid(mTagSubmit.getTid());
		tagMapper.deleteTagHot(tagHotModel);
	}
	
	

	@Override
	public void insertRedis(MTagSubmit mTagSubmit) {
		List<Long> tidList=mTagSubmit.getTidList();
		List<Long> numList=mTagSubmit.getNumList();
		for(int i=0;i<tidList.size();i++){
			Long tid=tidList.get(i);
			Long num=numList.get(i);
			if(num!=null){
				TagHotModel thm=new TagHotModel();
				thm.setTid(tid);
				thm.setOrderNum(num.intValue());
				tagMapper.updateTagOrderNum(thm);
			}
		}
		List<TagHotModel> list=tagMapper.queryTagHotModels();
		redisHotService.insertChannelTags(list);
	}

	@Override
	public void insertTagHotImage(MTagSubmit mTagSubmit) throws Exception {
		TagHotModel tagHotModel = new TagHotModel();
		tagHotModel.setTid(mTagSubmit.getTid());
		MultipartFile imageUrl = mTagSubmit.getImageUrl();
		String imageUrlStr = AliyunUploadUtils.uploadSystemImages(
				imageUrl.getInputStream(), imageUrl.getBytes().length,
				imageUrl.getContentType());
		tagHotModel.setImageUrl(imageUrlStr);
		tagHotModel.setTagType(2);
		tagHotModel.setCreateTime(System.currentTimeMillis());
		tagMapper.insertTagHotImage(tagHotModel);
	}

	@Override
	public TagModel getTagModel(TagModel tagModel) {
		TagModel tModel = tagMapper.getTagModel(tagModel);
		if (tModel == null) {
			tModel = new TagModel();
		}
		return tModel;
	}

	@Override
	public void queryTagList(MTagSubmit mTagSubmit) {
		long count = tagMapper.queryTagListCount(mTagSubmit);
		mTagSubmit.computerTotalPage(count);
		List<Map<String, Object>> objList = tagMapper.queryTagList(mTagSubmit);
		mTagSubmit.setObjList(objList);
	}

	@Override
	public void queryTagHotList(MTagSubmit mTagSubmit) {
		long count = tagMapper.queryTagHotListCount(mTagSubmit);
		mTagSubmit.computerTotalPage(count);
		List<Map<String, Object>> objList = tagMapper
				.queryTagHotList(mTagSubmit);
		mTagSubmit.setObjList(objList);
	}
	@Override
	public void queryTagHotImageList(MTagSubmit mTagSubmit) {
		long count = tagMapper.queryTagHotImageListCount(mTagSubmit);
		mTagSubmit.computerTotalPage(count);
		List<Map<String, Object>> objList = tagMapper
				.queryTagHotImageList(mTagSubmit);
		mTagSubmit.setObjList(objList);
	}

	@Override
	public Map<String, Object> updateTagModel(TagModel tagModel, long cid) {
		Map<String, Object> map = new HashMap<String, Object>();
		long tid = tagModel.getTid();
		if (tid != 0) {
			TagModel tagM = tagMapper.getTagModel(tagModel);
			ContentModel contentModel = contentMapper.getContentModel(cid);
			String description = contentModel.getDescription();
			String tag = tagM.getTagText();
			String desc = description.replace("#" + tag + "#",
					"#" + tagModel.getTagText() + "#");
			ContentModel cm = new ContentModel();
			cm.setDescription(desc);
			cm.setCid(cid);
			map.put("oldText", tag);
			contentMapper.updateContentDescription(cm);
			tagMapper.updateTagModel(tagModel);
		} else {
			tagModel.setTagType(1);
			tagMapper.insertTagModel(tagModel);
			ContentTagModel contentTagModel = new ContentTagModel();
			contentTagModel.setTid(tagModel.getTid());
			contentTagModel.setCid(cid);
			tagMapper.insertContentTag(contentTagModel);
			Map<String, Object> mapt = new HashMap<String, Object>();
			mapt.put("cid", cid);
			mapt.put("text", "#" + tagModel.getTagText() + "#");
			contentMapper.insertContentDescription(mapt);
		}

		map.put("cid", cid);
		map.put("tid", tagModel.getTid());
		return map;
	}

	@Override
	public OtherTagModel selectOtherTagModel(int tagType) {
		OtherTagModel otm = tagMapper.selectOtherTagModel(tagType);
		return otm;
	}

	public IRedisHotService getRedisHotService() {
		return redisHotService;
	}

	public void setRedisHotService(IRedisHotService redisHotService) {
		this.redisHotService = redisHotService;
	}

	public TagMapper getTagMapper() {
		return tagMapper;
	}

	public void setTagMapper(TagMapper tagMapper) {
		this.tagMapper = tagMapper;
	}

	public void setContentMapper(ContentMapper contentMapper) {
		this.contentMapper = contentMapper;
	}

	public void setSystemSynService(ISystemSynService systemSynService) {
		this.systemSynService = systemSynService;
	}
	

}
