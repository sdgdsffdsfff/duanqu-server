package com.duanqu.manager.dao;

import java.util.List;

import com.duanqu.common.model.FilterWordModel;
import com.duanqu.common.submit.MFilterWordSubmit;

public interface FilterWordMapper {
	
	/**
	 * @param mFilterWordSubmit
	 * @return
	 * 查询敏感词列表
	 */
	List<FilterWordModel> queryFilterWordModels(MFilterWordSubmit mFilterWordSubmit);
	
	/**
	 * @param mFilterWordSubmit
	 * @return
	 * 查询总数
	 */
	long queryFilterWordModelsCount(MFilterWordSubmit mFilterWordSubmit);
	
	/**
	 * @param filterWordModel
	 * 添加敏感词
	 */
	void insertFilterWordModel(FilterWordModel filterWordModel);
	
	/**
	 * @param id
	 * 删除敏感词
	 */
	void deleteFilterWordModel(int id);
	
	FilterWordModel getFilterWordModel(int id);

}
