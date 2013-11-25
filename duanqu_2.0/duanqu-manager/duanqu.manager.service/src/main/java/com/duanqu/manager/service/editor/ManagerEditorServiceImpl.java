package com.duanqu.manager.service.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.duanqu.common.dao.editor.EditorMapper;
import com.duanqu.common.model.ActiveContentModel;
import com.duanqu.common.model.ActiveInfoModel;
import com.duanqu.common.model.BannerInfoModel;
import com.duanqu.common.model.EditorTagModel;
import com.duanqu.common.model.EditorTalentInfoModel;
import com.duanqu.common.model.EditorTalentModel;
import com.duanqu.manager.dao.TagMapper;
import com.duanqu.redis.service.hot.IRedisHotService;

public class ManagerEditorServiceImpl implements IManagerEditorService {
	
	private EditorMapper editorMapper;
	
	private IRedisHotService redisHotService;
	
	private TagMapper tagMapper;

	@Override
	public void insertActiveInfo(ActiveInfoModel activeInfoModel,long cid) {
		ActiveContentModel activeContentModel=new ActiveContentModel();
		editorMapper.insertActiveInfo(activeInfoModel);
		activeContentModel.setActiveId(activeInfoModel.getId());
		activeContentModel.setCid(cid);
		editorMapper.insertActiveContent(activeContentModel);
	}

	@Override
	public void insertEditorTag(EditorTagModel editorTagModel) {
		editorMapper.insertEditorTag(editorTagModel);

	}

	@Override
	public void insertEditorTalent(EditorTalentModel editorTalentModel) {
		editorMapper.insertEditorTalent(editorTalentModel);

	}

	@Override
	public void insertBannerInfo(BannerInfoModel bannerInfoModel) {
		editorMapper.insertBannerInfo(bannerInfoModel);

	}

	@Override
	public void updateActiveContent(ActiveContentModel activeContentModel) {
		editorMapper.updateActiveContent(activeContentModel);

	}

	@Override
	public void deleteEditorTag(int id) {
		editorMapper.deleteEditorTag(id);

	}

	@Override
	public void deleteEditorTalent(int id) {
		editorMapper.deleteEditorTalent(id);

	}
	
	
	@Override
	public void insertEditorTalentInfo(
			EditorTalentInfoModel editorTalentInfoModel) {
		editorMapper.insertEditorTalentInfo(editorTalentInfoModel);
		
	}

	@Override
	public List<EditorTalentInfoModel> queryEditorTalentInfoModels() {
		
		return editorMapper.queryEditorTalentInfoModels();
	}

	@Override
	public List<BannerInfoModel> queryBannerInfoModels() {
		
		return editorMapper.queryBannerInfoModels();
	}

	@Override
	public List<EditorTagModel> queryEditorTagModels() {
		
		return editorMapper.queryEditorTagModels();
	}

	@Override
	public List<Map<String, Object>> queryTagInfo() {
		List<Map<String, Object>> list=editorMapper.queryTagInfo();
		List<Map<String, Object>> editorList=tagMapper.queryEditorTagList();
		list.addAll(0, editorList);
		if(list.size()>500){
			list=list.subList(0, 500);
		}
		if(list!=null&&list.size()>0){
			List<String> tags = new ArrayList<String>();
			for(Iterator<Map<String, Object>> iterator=list.iterator();iterator.hasNext();){
				Map<String, Object> map=iterator.next();
				tags.add((String)map.get("tag_text"));
			}
			redisHotService.addTag(tags);
		}
		return list;
	}
	
	

	@Override
	public List<EditorTalentModel> queryEditorTalentModels() {
		
		return editorMapper.queryEditorTalentModels();
	}

	public EditorMapper getEditorMapper() {
		return editorMapper;
	}

	public void setEditorMapper(EditorMapper editorMapper) {
		this.editorMapper = editorMapper;
	}

	public IRedisHotService getRedisHotService() {
		return redisHotService;
	}

	public void setRedisHotService(IRedisHotService redisHotService) {
		this.redisHotService = redisHotService;
	}

	public void setTagMapper(TagMapper tagMapper) {
		this.tagMapper = tagMapper;
	}
	
	

}
