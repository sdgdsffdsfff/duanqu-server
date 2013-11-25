package com.duanqu.redis.service.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.duanqu.common.DuanquConfig;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.vo.GroupForm;
import com.duanqu.common.vo.SimpleUserForm;
import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.service.user.IRedisUserService;
import com.duanqu.redis.utils.key.GroupKeyManager;

public class RedisGroupServiceImpl  extends BaseRedisService implements IRedisGroupService {
	
	
	
	IRedisUserService redisUserService;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<GroupForm> loadGroups(long uid) {
		List<GroupForm> groups = new ArrayList<GroupForm>();
		for(String groupName : DuanquConfig.getGroups()){
			GroupForm group = new GroupForm();
			group.setName(groupName);
			Long size = userTemplate.boundSetOps(GroupKeyManager.getGroupUsersKey(group.getName(), uid)).size();
			group.setCount(size.intValue());
			groups.add(group);
		}
		Set myMembers = userTemplate.boundZSetOps(GroupKeyManager.getUserGroupsKey(uid)).reverseRange(0, -1);
		Iterator it = myMembers.iterator();
		while (it.hasNext()) {
			String groupName = (String)it.next();
			if (!DuanquConfig.getGroups().contains(groupName)){
				GroupForm group = new GroupForm();
				group.setName(groupName);
				Long size = userTemplate.boundSetOps(GroupKeyManager.getGroupUsersKey(group.getName(), uid)).size();
				group.setCount(size.intValue());
				groups.add(group);
			}
		}
		return groups;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SimpleUserForm> loadUsers(long uid, String groupName) {
		Set members = userTemplate.boundSetOps(GroupKeyManager.getGroupUsersKey(groupName, uid)).members();
		List<SimpleUserForm> users = new ArrayList<SimpleUserForm>();
		Iterator it = members.iterator();
		while (it.hasNext()) {
			Long userId = Long.parseLong((String)it.next());
			UserModel model = redisUserService.getUser(userId);
			users.add(model.asSimpleUserForm());
		}
		return users;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void addGroup(String groupName, long uid) {
		userTemplate.boundZSetOps(GroupKeyManager.getUserGroupsKey(uid)).add(groupName,System.currentTimeMillis());
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean isExist(String groupName, long uid) {
		if (DuanquConfig.getGroups().contains(groupName)){
			return true;
		}
		Long rank = userTemplate.boundZSetOps(GroupKeyManager.getUserGroupsKey(uid)).rank(groupName);
		return rank != null;
	}

	public void setRedisUserService(IRedisUserService redisUserService) {
		this.redisUserService = redisUserService;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addUsers(String groupName, long uid, Set uids) {
		Iterator it = uids.iterator();
		while (it.hasNext()){
			userTemplate.boundSetOps(GroupKeyManager.getGroupUsersKey(groupName, uid)).add(String.valueOf(it.next()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void editGroup(String oldGroupName, String newGroupName,long uid) {
		Double score = userTemplate.boundZSetOps(GroupKeyManager.getUserGroupsKey(uid)).score(oldGroupName);
		boolean add = userTemplate.boundZSetOps(GroupKeyManager.getUserGroupsKey(uid)).add(newGroupName, score);
		if (add){
			userTemplate.rename(GroupKeyManager.getGroupUsersKey(oldGroupName, uid), GroupKeyManager.getGroupUsersKey(newGroupName, uid));
			//删除老GroupName
			userTemplate.boundZSetOps(GroupKeyManager.getUserGroupsKey(uid)).remove(oldGroupName);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteGroupUser(long uid, String groupName, long delUid) {
		return userTemplate.boundSetOps(GroupKeyManager.getGroupUsersKey(groupName, uid)).remove(String.valueOf(delUid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteGroup(long uid, String groupName) {
		boolean delete = userTemplate.boundZSetOps(GroupKeyManager.getUserGroupsKey(uid)).remove(groupName);
		if (delete){
			userTemplate.delete(GroupKeyManager.getGroupUsersKey(groupName, uid));
		}
	}
	
}
