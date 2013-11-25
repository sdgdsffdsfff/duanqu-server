package com.duanqu.manager.service.filter;

import java.util.List;

import com.duanqu.common.model.FilterWordModel;
import com.duanqu.common.submit.MFilterWordSubmit;
import com.duanqu.manager.dao.FilterWordMapper;
import com.duanqu.redis.service.syn.system.ISystemSynService;

public class ManagerFilterServiceImpl implements IManagerFilterService {
	
	private FilterWordMapper filterWordMapper;
	
	private ISystemSynService systemSynService;

	@Override
	public void queryFilterWordModels(MFilterWordSubmit mFilterWordSubmit) {
		long count=filterWordMapper.queryFilterWordModelsCount(mFilterWordSubmit);
		mFilterWordSubmit.computerTotalPage(count);
		List<FilterWordModel> objList=filterWordMapper.queryFilterWordModels(mFilterWordSubmit);
		mFilterWordSubmit.setObjList(objList);
	}

	@Override
	public void insertFilterWordModel(FilterWordModel filterWordModel) {
		filterWordMapper.insertFilterWordModel(filterWordModel);
		systemSynService.addBadword(filterWordModel.getFilterText());
	}
	@Override
	public void deleteFilterWordModel(int id) {
		FilterWordModel filterWordModel=filterWordMapper.getFilterWordModel(id);
		if(filterWordModel!=null){
			filterWordMapper.deleteFilterWordModel(id);
			systemSynService.deleteBadword(filterWordModel.getFilterText());
		}
		}
	public void setFilterWordMapper(FilterWordMapper filterWordMapper) {
		this.filterWordMapper = filterWordMapper;
	}

	public void setSystemSynService(ISystemSynService systemSynService) {
		this.systemSynService = systemSynService;
	}
	
	
	

}
