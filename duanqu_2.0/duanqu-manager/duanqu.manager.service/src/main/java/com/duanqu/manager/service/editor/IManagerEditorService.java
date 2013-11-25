package com.duanqu.manager.service.editor;

import java.util.List;
import java.util.Map;

import com.duanqu.common.model.ActiveContentModel;
import com.duanqu.common.model.ActiveInfoModel;
import com.duanqu.common.model.BannerInfoModel;
import com.duanqu.common.model.EditorTagModel;
import com.duanqu.common.model.EditorTalentInfoModel;
import com.duanqu.common.model.EditorTalentModel;

public interface IManagerEditorService {
	void insertActiveInfo(ActiveInfoModel actionInfoModel,long cid);//新增活动
	void insertEditorTag(EditorTagModel editorTagModel);//新增推存标签
	void insertEditorTalent(EditorTalentModel editorTalentModel);//新增推存达人
	void insertBannerInfo(BannerInfoModel bannerInfoModel);//新增运营条活广告信息
	void updateActiveContent(ActiveContentModel activeContentModel);//更新活动排序
	void deleteEditorTag(int id);//删除编辑推存标签
	void deleteEditorTalent(int id);//删除编辑推存达人榜
	List<BannerInfoModel> queryBannerInfoModels();//显示运营条内容
	List<EditorTagModel> queryEditorTagModels();//显示编辑推存的标签
	List<Map<String, Object>> queryTagInfo();//查询热门标签
	List<EditorTalentModel> queryEditorTalentModels();//查询达人榜
	void insertEditorTalentInfo(EditorTalentInfoModel editorTalentInfoModel);//插入达人榜信息表
	List<EditorTalentInfoModel> queryEditorTalentInfoModels();//查询达人榜信息表
	
	

}
