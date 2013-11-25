package com.duanqu.manager.service.hot;


import com.duanqu.manager.service.content.IManagerContentService;
import com.duanqu.manager.service.user.IManagerUserService;

public class ManagerHotServiceImpl implements IManagerHotService {

	
	IManagerUserService managerUserService;
	
	IManagerContentService managerContentService;
	
	@Override
	public void insertHotList() {
		//managerContentService.insertHotContentList();
		managerUserService.inserHotUserList();
	}
	

	@Override
	public void insertHotContentList() {
		managerContentService.insertHotContentList();
	}


	public IManagerUserService getManagerUserService() {
		return managerUserService;
	}

	public void setManagerUserService(IManagerUserService managerUserService) {
		this.managerUserService = managerUserService;
	}

	public IManagerContentService getManagerContentService() {
		return managerContentService;
	}

	public void setManagerContentService(
			IManagerContentService managerContentService) {
		this.managerContentService = managerContentService;
	}
	
	

}
