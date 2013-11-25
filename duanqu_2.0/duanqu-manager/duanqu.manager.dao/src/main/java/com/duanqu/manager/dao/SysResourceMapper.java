package com.duanqu.manager.dao;

import java.util.List;

import com.duanqu.common.model.SysResourceModel;
import com.duanqu.manager.submit.ManagerSysResourceSubmit;

public interface SysResourceMapper {
	
	/**
	 * @param managerSysResourceSubmit
	 * @return
	 * 查询上传的资源列表
	 */
	List<SysResourceModel> querySysResourceList(ManagerSysResourceSubmit managerSysResourceSubmit);
	
	long querySysResourceCount(ManagerSysResourceSubmit managerSysResourceSubmit);
	
	/**
	 * @param sysResourceModel
	 * 添加资源
	 */
	void insertSysResourceModel(SysResourceModel sysResourceModel);
	
	/**
	 * @param id
	 * 删除资源
	 */
	void deleteSysResourceModel(long id);
	
	

}
