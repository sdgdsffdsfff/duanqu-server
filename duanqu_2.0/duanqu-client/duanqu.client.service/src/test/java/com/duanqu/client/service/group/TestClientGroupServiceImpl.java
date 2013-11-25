package com.duanqu.client.service.group;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.duanqu.client.service.comment.BaseTestServiceImpl;
import com.duanqu.common.model.GroupModel;
import com.duanqu.common.model.GroupRelationModel;

public class TestClientGroupServiceImpl extends BaseTestServiceImpl {
	
	@Resource
	IClientGroupService clientGroupService;
	
	
	//@Test
	@Rollback(false)
	public void testInsertGroupModel(){
		GroupModel groupModel=new GroupModel();
		groupModel.setUid(143);
		groupModel.setGroupName("家人");
		groupModel.setCreateTime(System.currentTimeMillis());
		clientGroupService.insertGroupModel(groupModel);
	}
	
	public void testDeleteGroupModel(){
		clientGroupService.deleteGroupModel(15, "同学");
	}
	@Test
	@Rollback(false)
	public void testUploadGroupModel(){
		
		clientGroupService.updateGroupModel("大学同学","测试",143);
		
	}
	
	
	//@Test
	@Rollback(false)
	public void testInsertGroupRelationModle(){
		/*GroupRelationModel groupRelationModel=new GroupRelationModel();
		groupRelationModel.setGid(6);
		groupRelationModel.setRelId(1);
		groupRelationModel.setFuid(144);
		groupRelationModel.setCreateTime(System.currentTimeMillis());
		groupRelationModel.setAddTime(System.currentTimeMillis());
	*/	
		clientGroupService.insertGroupRelation("朋友",143,145);
	//	clientGroupService.deleteGroupRelation("家人", 143, 145);
	}
	
	

}
