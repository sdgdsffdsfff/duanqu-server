package com.duanqu.manager.service.resource;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.DuanquUtils;
import com.duanqu.common.model.SysResourceModel;
import com.duanqu.manager.dao.SysResourceMapper;
import com.duanqu.manager.submit.ManagerSysResourceSubmit;
import com.duanqu.redis.service.resource.IRedisResourceService;

public class ManagerResourceServiceImpl implements IManagerResourceService {
	Log logger = LogFactory.getLog(SysResourceMapper.class);
	private SysResourceMapper sysResourceMapper;
	
	private IRedisResourceService redisResourceService;
	
	@Override
	public void deleteSysResource(long id) {
		sysResourceMapper.deleteSysResourceModel(id);
		try{
			redisResourceService.deleteSysResource(id);
		}catch(Exception e){
			logger.error("同步资源出错！e="+e);
		}

	}

	@Override
	public void insertSysResourceModel(ManagerSysResourceSubmit managerSysResourceSubmit) {
		SysResourceModel sysResourceModel=new SysResourceModel();
		MultipartFile resourceFile=managerSysResourceSubmit.getResourceFile();
		MultipartFile resourceIconFile=managerSysResourceSubmit.getResourceIconFile();
		MultipartFile resourceMusicFile=managerSysResourceSubmit.getResourceMusicFile();
		int type=managerSysResourceSubmit.getType();
		String resourceUrl="";
		String resourceIconUrl="";
		String resourceMusicUrl="";
		int size=0;
		String resourceMd5="";
		try {
			if(resourceFile!=null){
				resourceUrl=AliyunUploadUtils.uploadSystemResource(resourceFile.getInputStream(), resourceFile.getBytes().length, resourceFile.getContentType(), type,resourceFile.getOriginalFilename());
				size=resourceFile.getBytes().length;
				resourceMd5=DuanquUtils.md5File(resourceFile);
			}	
		} catch (Exception e) {
			logger.error("上传资源包失败"+e.getMessage());
		}
		try {
			if(resourceIconFile!=null)
				resourceIconUrl=AliyunUploadUtils.uploadSystemIcon(resourceIconFile.getInputStream(), resourceIconFile.getBytes().length, resourceIconFile.getContentType(), type);
		} catch (Exception e) {
			logger.error("上传展示图标失败"+e.getMessage());
		}
		if(type==1){
			try {
				if(resourceMusicFile!=null)
					resourceMusicUrl=AliyunUploadUtils.uploadSystemMusic(resourceMusicFile.getInputStream(), resourceMusicFile.getBytes().length, resourceMusicFile.getContentType());
			} catch (Exception e) {
				logger.error("上传展示背景音乐失败"+e.getMessage());
			}
		}
		sysResourceModel.setDescription(managerSysResourceSubmit.getDescription());
		sysResourceModel.setType(type);
		sysResourceModel.setResourceIconUrl(resourceIconUrl);
		sysResourceModel.setResourceMusicUrl(resourceMusicUrl);
		sysResourceModel.setResourceUrl(resourceUrl);
		sysResourceModel.setSize(size);
		sysResourceModel.setResourceMd5(resourceMd5);
		sysResourceModel.setCreateTime(System.currentTimeMillis());
		sysResourceMapper.insertSysResourceModel(sysResourceModel);
		
		try{
			redisResourceService.addSysResource(sysResourceModel);
		}catch(Exception e){
			logger.error("同步资源出错！e="+e);
		}

	}

	@Override
	public void querySysResourceList(
			ManagerSysResourceSubmit managerSysResourceSubmit) {
		long count=sysResourceMapper.querySysResourceCount(managerSysResourceSubmit);
		managerSysResourceSubmit.computerTotalPage(count);
		List<SysResourceModel> list=sysResourceMapper.querySysResourceList(managerSysResourceSubmit);
		managerSysResourceSubmit.setObjList(list);

	}

	public void setSysResourceMapper(SysResourceMapper sysResourceMapper) {
		this.sysResourceMapper = sysResourceMapper;
	}

	public void setRedisResourceService(IRedisResourceService redisResourceService) {
		this.redisResourceService = redisResourceService;
	}
	
}
