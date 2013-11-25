package com.duanqu.manager.service.filter;

import com.duanqu.common.model.FilterWordModel;
import com.duanqu.common.submit.MFilterWordSubmit;

public interface IManagerFilterService {
	
	/**
	 * @param mFilterWordSubmit
	 * 查询敏感词列表
	 */
	void queryFilterWordModels(MFilterWordSubmit mFilterWordSubmit);
	
	void insertFilterWordModel(FilterWordModel filterWordModel);
	
	void deleteFilterWordModel(int id);
	
	

}
