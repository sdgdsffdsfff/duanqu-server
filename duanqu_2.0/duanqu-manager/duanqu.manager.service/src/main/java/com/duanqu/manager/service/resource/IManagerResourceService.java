package com.duanqu.manager.service.resource;

import com.duanqu.common.model.SysResourceModel;
import com.duanqu.manager.submit.ManagerSysResourceSubmit;

public interface IManagerResourceService {
	
	/**
	 * @param managerSysResourceSubmit
	 * 查询上传资源列表
	 */
	void querySysResourceList(ManagerSysResourceSubmit managerSysResourceSubmit);
	
	/**
	 * @param sysResourceModel
	 * 上传资源
	 */
	void insertSysResourceModel(ManagerSysResourceSubmit managerSysResourceSubmit);
	
	/**
	 * @param id
	 * 删除资源
	 */
	void deleteSysResource(long id);

}
