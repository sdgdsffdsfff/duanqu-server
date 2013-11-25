package com.duanqu.client.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanqu.common.DuanquConfig;
import com.duanqu.common.DuanquErrorCode;
import com.duanqu.common.DuanquUtils;
import com.duanqu.common.NoticeMessage;
import com.duanqu.common.Result;
import com.duanqu.common.bean.GroupBean;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.vo.GroupForm;
import com.duanqu.common.vo.SimpleUserForm;
import com.duanqu.redis.service.group.IRedisGroupService;
import com.duanqu.redis.service.user.IRedisRelationshipService;

@Controller
public class GroupController extends BaseController {

	@Resource
	IRedisGroupService redisGroupService;
	@Resource
	IRedisRelationshipService redisRelationshipService;

	@RequestMapping(value = "/group/list", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result listGroups(@RequestParam("token") String token) {
		UserModel model = super.getUser(token);
		Result result = new Result();
		if (model != null && model.getUid() > 0){
			List<GroupForm> groups = redisGroupService.loadGroups(model.getUid());
			result.setCode(200);
			result.setData(groups);
			result.setMessage("获取成功！");
			result.setTime(System.currentTimeMillis());
		}else{
			result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		return result;

	}
	
	@RequestMapping(value = "/group/add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result addGroup(@RequestParam("uids") String uids,
			@RequestParam("groupName") String groupName,
			@RequestParam("token") String token) {
		Result result = new Result();
		UserModel model = super.getUser(token);
		if (!StringUtils.hasText(uids) || !StringUtils.hasText(groupName)){
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}else{
			groupName = groupName.trim();
			if (model != null && model.getUid() > 0){
				if (redisGroupService.isExist(groupName, model.getUid())){
					result.setCode(DuanquErrorCode.GROUP_EXIST.getCode());
					result.setData("");
					result.setMessage(DuanquErrorCode.GROUP_EXIST.getMessage());
					result.setTime(System.currentTimeMillis());
				}else{
					String[] uidArr = uids.split(",");
					Set<String> uidSet = new HashSet<String>();
					for (String uidStr : uidArr ){
						uidSet.add(uidStr);
					}
					redisGroupService.addGroup(groupName, model.getUid());
					redisGroupService.addUsers(groupName, model.getUid(), uidSet);
					result.setCode(200);
					result.setData("");
					result.setMessage("添加成功！");
					result.setTime(System.currentTimeMillis());
				}
			}else{
				result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
				result.setData("");
				result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
				result.setTime(System.currentTimeMillis());
			}
		}
		
		if (result.getCode() == 200){
			try{
				GroupBean group = new GroupBean();
				group.setGroupName(groupName);
				group.setUid(model.getUid());
				group.setUids(uids);
				redisJMSMessageService.insertGroupAddQueue(group);
				duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.GROUP_ADD));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	@RequestMapping(value = "/group/users/add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result addGroupUsers(@RequestParam("uids") String uids, //用户Id用“,” 分开
			@RequestParam("groupName") String groupNames,//组名可以多选 用,
			@RequestParam("token") String token) {
		UserModel model = super.getUser(token);
		Result result = new Result();
		if (!StringUtils.hasText(uids) || !StringUtils.hasText(groupNames)){
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}else{
			
			if (model != null && model.getUid() > 0){
				String[] groups = groupNames.split(",");
				for (String group : groups){
					if (redisGroupService.isExist(group, model.getUid())){
						String[] uidArr = uids.split(",");
						Set<Long> uidSet = new HashSet<Long>();
						for (String uidStr : uidArr) {
							if (uidStr.trim().length() > 0) {
								try {
									long fid = Long.parseLong(uidStr);
									UserModel user = redisUserService.getUser(fid);
									if (user != null && user.getUid() > 0) {
										if (redisRelationshipService.isFriend(model.getUid(), fid)){
											uidSet.add(fid);
										}
									}
								} catch (Exception e) {
								}
							}
						}
						redisGroupService.addUsers(group, model.getUid(), uidSet);
					}
				}
				result.setCode(200);
				result.setData("");
				result.setMessage("添加成功！");
				result.setTime(System.currentTimeMillis());
			}else{
				result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
				result.setData("");
				result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
				result.setTime(System.currentTimeMillis());
			}
		}
		
		if (result.getCode() == 200){
			if (result.getCode() == 200){
				try{
					GroupBean group = new GroupBean();
					group.setGroupName(groupNames);
					group.setUid(model.getUid());
					group.setUids(uids);
					redisJMSMessageService.insertGroupUsersAddQueue(group);
					duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.GROUP_USER_ADD));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/group/users/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result deleteGroupUsers(@RequestParam(value="uid",required=true,defaultValue="0") long uid,
			@RequestParam("groupName") String groupName,
			@RequestParam("token") String token) {
		UserModel model = super.getUser(token);
		Result result = new Result();
		if (uid == 0 || !StringUtils.hasText(groupName)){
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}else{
			if (model != null && model.getUid() > 0){
				if (redisGroupService.isExist(groupName, model.getUid())){
					boolean bool = redisGroupService.deleteGroupUser(model.getUid(), groupName, uid);
					if (bool){
						result.setCode(200);
						result.setData("");
						result.setMessage("删除成功！");
						result.setTime(System.currentTimeMillis());
					}else{
						result.setCode(DuanquErrorCode.SYSTEM_ERROR.getCode());
						result.setData("");
						result.setMessage(DuanquErrorCode.SYSTEM_ERROR.getMessage());
						result.setTime(System.currentTimeMillis());
					}
				}
			}else{
				result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
				result.setData("");
				result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
				result.setTime(System.currentTimeMillis());
			}
		}
		
		if (result.getCode() == 200){
			if (result.getCode() == 200){
				try{
					GroupBean group = new GroupBean();
					group.setGroupName(groupName);
					group.setUid(model.getUid());
					group.setUids(String.valueOf(uid));
					redisJMSMessageService.insertGroupUsersDeleteQueue(group);
					duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.GROUP_USER_DELETE));
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/group/users", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result listGroupUsers(
			@RequestParam("groupName")String groupName,
			@RequestParam("token") String token) {
		UserModel model = super.getUser(token);
		Result result = new Result();
		if (model != null && model.getUid() > 0){
			List<SimpleUserForm> users = redisGroupService.loadUsers(model.getUid(), groupName);
			result.setCode(200);
			result.setData(users);
			result.setMessage("获取成功！");
			result.setTime(System.currentTimeMillis());
		}else{
			result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		return result;
	}
	
	@RequestMapping(value = "/group/edit", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result editGroup(@RequestParam("oldGroupName")String oldGroupName,
			@RequestParam("newGroupName") String newGroupName,
			@RequestParam("token") String token) {
		UserModel model = super.getUser(token);
		Result result = new Result();
		if (!StringUtils.hasText(oldGroupName) || !StringUtils.hasText(newGroupName)){
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}else{
			newGroupName = newGroupName.trim();
			if (DuanquConfig.getGroups().contains(newGroupName)){
				result.setCode(DuanquErrorCode.GROUP_DEFAULT.getCode());
				result.setData("");
				result.setMessage(DuanquErrorCode.GROUP_DEFAULT.getMessage());
				result.setTime(System.currentTimeMillis());
			}else{
				
				if (model != null && model.getUid() > 0){
					if (redisGroupService.isExist(oldGroupName, model.getUid())){
						if (redisGroupService.isExist(newGroupName, model.getUid())){
							result.setCode(DuanquErrorCode.GROUP_EXIST.getCode());
							result.setData("");
							result.setMessage(DuanquErrorCode.GROUP_EXIST.getMessage());
							result.setTime(System.currentTimeMillis());
						}else{
							redisGroupService.editGroup(oldGroupName, newGroupName, model.getUid());
							result.setCode(200);
							result.setData("");
							result.setMessage("编辑成功！");
							result.setTime(System.currentTimeMillis());
						}
					}else{
						result.setCode(DuanquErrorCode.GROUP_NOT_EXIST.getCode());
						result.setData("");
						result.setMessage(DuanquErrorCode.GROUP_NOT_EXIST.getMessage());
						result.setTime(System.currentTimeMillis());
					}
				}else{
					result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
					result.setData("");
					result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
					result.setTime(System.currentTimeMillis());
				}
			}
		}
		
		if (result.getCode() == 200){
			if (result.getCode() == 200){
				try{
					GroupBean group = new GroupBean();
					group.setGroupName(newGroupName);
					group.setOldGroupName(oldGroupName);
					group.setUid(model.getUid());
					redisJMSMessageService.insertGroupEditQueue(group);
					duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.GROUP_EDIT));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/group/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result deleteGroup(
			@RequestParam("groupName") String groupName,
			@RequestParam("token") String token) {
		UserModel model = super.getUser(token);
		Result result = new Result();
		if (!StringUtils.hasText(groupName)){
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}else{
			if (DuanquConfig.getGroups().contains(groupName)){
				result.setCode(DuanquErrorCode.GROUP_DEFAULT.getCode());
				result.setData("");
				result.setMessage(DuanquErrorCode.GROUP_DEFAULT.getMessage());
				result.setTime(System.currentTimeMillis());
			} else {
				
				if (model != null && model.getUid() > 0){
					if (redisGroupService.isExist(groupName, model.getUid())){
						redisGroupService.deleteGroup(model.getUid(), groupName);
						result.setCode(200);
						result.setData("");
						result.setMessage("删除成功！");
						result.setTime(System.currentTimeMillis());
					}else{
						result.setCode(DuanquErrorCode.GROUP_NOT_EXIST.getCode());
						result.setData("");
						result.setMessage(DuanquErrorCode.GROUP_NOT_EXIST.getMessage());
						result.setTime(System.currentTimeMillis());
					}
				}else{
					result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
					result.setData("");
					result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
					result.setTime(System.currentTimeMillis());
				}
			}
		}
		if (result.getCode() == 200){
			if (result.getCode() == 200){
				try{
					GroupBean group = new GroupBean();
					group.setGroupName(groupName);
					group.setUid(model.getUid());
					redisJMSMessageService.insertGroupDeleteQueue(group);
					duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.GROUP_DELETE));
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
		return result;
	}
}
