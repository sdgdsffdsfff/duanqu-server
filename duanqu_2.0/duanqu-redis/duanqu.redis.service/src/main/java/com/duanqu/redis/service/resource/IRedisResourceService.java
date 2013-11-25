package com.duanqu.redis.service.resource;

import java.util.List;

import com.duanqu.common.model.SysResourceModel;
import com.duanqu.common.vo.SysResourceForm;

public interface IRedisResourceService {
	
	/**
	 * 获取资源详细信息
	 * @param id
	 * @return
	 */
	public SysResourceModel getSysResource(long id);
	
	/**
	 * 添加资源信息
	 * @param res
	 */
	public void addSysResource(SysResourceModel res);
	
	/**
	 * 删除资源信息
	 * @param res
	 */
	public void deleteSysResource(long id);
	
	/**
	 * 根据类别获取资源列表
	 * @param uid
	 * @param type
	 * @return
	 */
	public List<SysResourceForm> loadSysResources(long uid,int type,int page,int pageSize);
	
	/**
	 * 统计某种资源总数
	 * @param type
	 * @return
	 */
	public int countSysResources(int type);
	
	/**
	 * 判断该用户的该条资源有没有解锁
	 * @param uid
	 * @param type
	 * @param id
	 * @return
	 */
	public boolean checkResource(long uid,int type,long id);
	
	/**把用户id添加到对应的解锁资源set
	 * @param uid
	 * @param type
	 * @param id
	 */
	public void saveResource(long uid,int type,long id);
	

}
