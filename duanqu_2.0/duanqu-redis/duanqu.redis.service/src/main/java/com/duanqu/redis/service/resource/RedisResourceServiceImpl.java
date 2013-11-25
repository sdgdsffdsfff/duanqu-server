package com.duanqu.redis.service.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisMap;

import com.duanqu.common.model.SysResourceModel;
import com.duanqu.common.vo.SysResourceForm;
import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.utils.key.SystemKeyManager;

public class RedisResourceServiceImpl extends BaseRedisService implements IRedisResourceService {
	
	private final HashMapper<SysResourceModel, String, String> resourceMapper = new DecoratingStringHashMapper<SysResourceModel>(
			new JacksonHashMapper<SysResourceModel>(SysResourceModel.class));

	@Override
	public SysResourceModel getSysResource(long id) {
		return resourceMapper.fromHash(getContent(id));
	}
	
	@SuppressWarnings("unchecked")
	private RedisMap<String, String> getContent(long id) {
		return new DefaultRedisMap<String, String>(SystemKeyManager.getResourceKey(id), jmsTemplate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addSysResource(SysResourceModel res) {
		if (res != null){
			String listKey = getResListKey(res.getType());
			if (listKey != null){
				//添加资源信息
				jmsTemplate.boundHashOps(SystemKeyManager.getResourceKey(res.getId())).putAll(resourceMapper.toHash(res));
				//添加索引到列表
				jmsTemplate.boundZSetOps(listKey).add(String.valueOf(res.getId()), System.currentTimeMillis());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteSysResource(long id) {
		SysResourceModel res = this.getSysResource(id);
		if (res != null){
			String listKey = getResListKey(res.getType());
			if (listKey != null){
				//从资源列表中删除信息
				jmsTemplate.boundZSetOps(listKey).remove(String.valueOf(res.getId()));
				//删除资源信息
				jmsTemplate.delete(SystemKeyManager.getResourceKey(res.getId()));
			}
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysResourceForm> loadSysResources(long uid, int type,int begin ,int end) {
		/*DefaultStringRedisConnection connection = new DefaultStringRedisConnection(
				jmsTemplate.getConnectionFactory().getConnection());
		jmsTemplate.delete(SystemKeyManager.getExpressionAndPasterKey());
		int[] weights = {1,1};
		connection.zUnionStore(
				SystemKeyManager.getExpressionAndPasterKey(),
				Aggregate.MAX,weights,
				SystemKeyManager.getExpressionsKey(),
				SystemKeyManager.getPastersKey()
				);*/
		if(begin==0){
			jmsTemplate.delete(SystemKeyManager.getExpressionAndPasterKey());
			jmsTemplate.boundZSetOps(SystemKeyManager.getExpressionsKey()).unionAndStore(SystemKeyManager.getPastersKey(), SystemKeyManager.getExpressionAndPasterKey());
		}
		Set set = jmsTemplate.boundZSetOps(this.getResListKey(type)).reverseRange(begin, end);
		List<SysResourceForm> reses = new ArrayList<SysResourceForm>();
		if (set != null){
			Iterator it = set.iterator();
			while(it.hasNext()){
				Object obj = it.next();
				long id = 0;
				try{
					id = Long.parseLong(String.valueOf(obj));
				}catch(Exception e){
					id = 0;
				}
				if (id > 0){
					SysResourceModel res = this.getSysResource(id);
					if (res != null && res.getId() > 0){
						reses.add(res.asForm());
					}
				}
			}
		}
		return reses;
	}

	/**
	 * 根据资源信息获取存储列表的KEY
	 * @param res
	 * @return
	 */
	private String getResListKey(int type){
		String listKey = null;
		switch(type){
		case 1://1 背景音乐
			listKey = SystemKeyManager.getMusicsKey();
			break;
		case 2:// 2 表情
			listKey = SystemKeyManager.getExpressionsKey();
			break;
		case 3:// 3 贴纸
			listKey = SystemKeyManager.getPastersKey();
			break;
		case 4:// 表情和贴纸	
			listKey = SystemKeyManager.getExpressionAndPasterKey();
			break;
			
		}
		return listKey;
	}
	
	
	/**
	 * 根据资源类型获取资源对应用户的KEY
	 * @param type
	 * @param id
	 * @return
	 */
	private String getResUserSet(int type,long id){
		String setKey=null;
		switch(type){
		case 2:// 表情
			setKey=SystemKeyManager.getExpressionsSetKey(id);
			break;
		case 3:// 贴纸
			setKey=SystemKeyManager.getPastersSetKey(id);
			break;
		}
		return setKey;
	}
	
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkResource(long uid, int type, long id) {
		String setKey=getResUserSet(type, id);
		if(jmsTemplate.hasKey(setKey)){
		 return jmsTemplate.boundSetOps(setKey).isMember(String.valueOf(uid));
		}
		return false;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void saveResource(long uid, int type, long id) {
		String setKey=getResUserSet(type, id);
		if(setKey!=null)
		jmsTemplate.boundSetOps(setKey).add(String.valueOf(uid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public int countSysResources(int type) {
		String listKey = this.getResListKey(type);
		if (listKey != null){
			Long count = jmsTemplate.boundZSetOps(listKey).size();
			return count == null ? 0: count.intValue();
		}
		return 0;
	}
}
