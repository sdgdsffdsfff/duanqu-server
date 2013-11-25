package com.duanqu.client.service.group;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.duanqu.client.dao.ClientGroupMapper;
import com.duanqu.client.dao.ClientUserMapper;
import com.duanqu.common.DuanquConfig;
import com.duanqu.common.model.FriendModel;
import com.duanqu.common.model.GroupModel;
import com.duanqu.common.model.GroupRelationModel;

public class ClientGroupServiceImpl implements IClientGroupService {
	
	ClientGroupMapper clientGroupMapper;
	
	ClientUserMapper clientUserMapper;
	

	@Override
	public void insertGroupModel(GroupModel groupModel) {
		GroupModel gm=clientGroupMapper.getGroupModel(groupModel);
		if(gm==null){
			clientGroupMapper.insertGroupModel(groupModel);
		}
	}

	@Override
	public void deleteGroupModel(long uid, String groupName) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("groupName", groupName);
		clientGroupMapper.deleteGroupModel(map);
	}
	
	



	@Override
	public void deleteGroupRelation(String groupName, long groupUid, long fuid) {
		GroupModel groupModel=new GroupModel();
		groupModel.setGroupName(groupName);
		groupModel.setUid(groupUid);
		GroupModel newGroupModel=clientGroupMapper.getGroupModel(groupModel);
		if (newGroupModel != null){
			GroupRelationModel groupRelationModel=new GroupRelationModel();
			groupRelationModel.setGid(newGroupModel.getId());
			groupRelationModel.setFuid(fuid);
			clientGroupMapper.deleteGroupRelation(groupRelationModel);
		}
	}
	
	

	@Override
	public void updateGroupModel(String newGroupName,String oldGroupName,long uid) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("newGroupName", newGroupName);
		map.put("oldGroupName", oldGroupName);
		map.put("uid", uid);
		clientGroupMapper.updateGroupModel(map);
		
	}

	@Override
	public void insertGroupRelation(String groupName,long groupUid,long fuid) {
		GroupRelationModel groupRelationModel=new GroupRelationModel();
		GroupModel groupModel=new GroupModel();
		FriendModel friendModel=new FriendModel();
		groupModel.setGroupName(groupName);
		groupModel.setUid(groupUid);
		
		friendModel.setUid(groupUid);
		friendModel.setFid(fuid);
		FriendModel newFriendModel=clientUserMapper.getFriendModel(friendModel);
		GroupModel newGroupModel=clientGroupMapper.getGroupModel(groupModel);
		if(newGroupModel==null){
		List<String> list=DuanquConfig.getGroups();
	    if(list.contains(groupName)){
	    	groupModel.setCreateTime(System.currentTimeMillis());
	    	clientGroupMapper.insertGroupModel(groupModel);
	    	groupRelationModel.setGid(groupModel.getId());
	    }
		}else{
			groupRelationModel.setGid(newGroupModel.getId());
		}
		groupRelationModel.setRelId(newFriendModel.getId());
		groupRelationModel.setFuid(fuid);
		groupRelationModel.setCreateTime(newFriendModel.getCreateTime());
		groupRelationModel.setIsFriend(newFriendModel.getIsFriend());
		groupRelationModel.setIsTrue(newFriendModel.getIsTrue());
		groupRelationModel.setAddTime(System.currentTimeMillis());
		clientGroupMapper.insertGroupRelation(groupRelationModel);
	}

	public ClientGroupMapper getClientGroupMapper() {
		return clientGroupMapper;
	}

	public void setClientGroupMapper(ClientGroupMapper clientGroupMapper) {
		this.clientGroupMapper = clientGroupMapper;
	}

	public ClientUserMapper getClientUserMapper() {
		return clientUserMapper;
	}

	public void setClientUserMapper(ClientUserMapper clientUserMapper) {
		this.clientUserMapper = clientUserMapper;
	}
	
	

}
