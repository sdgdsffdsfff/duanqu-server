package com.duanqu.manager.service.dwr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.LikeContentModel;
import com.duanqu.common.model.RecommendModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.model.UserRecommendModel;
import com.duanqu.manager.dao.DwrMapper;
import com.duanqu.manager.dao.UserAdminMapper;
import com.duanqu.redis.service.hot.IRedisHotService;
import com.duanqu.redis.service.syn.content.IContentSynService;
import com.duanqu.redis.service.timeline.IRedisTimelineService;



public class ManagerDwrServiceImpl implements IManagerDwrService {
	
	
	private DwrMapper dwrMapper;
	
	private IContentSynService contentSynService;
	
	private UserAdminMapper userAdminMapper;
	private IRedisHotService redisHotService;
	
	private IRedisTimelineService redisTimelineService;
	

	@Override
	public Map<String, Object> checkContentIsPrivate(String cid,int type) {
		Map<String, Object> map=new HashMap<String, Object>();
		boolean flag=true;
		String message="";
		if (cid.endsWith(",")) {
			cid = cid.substring(0, cid.length() - 1);
		}
		String[] cidArray = cid.split(",");
		RecommendModel recommendModel=new RecommendModel();
		recommendModel.setType(type);
		for(int i=0;i<cidArray.length;i++){
			recommendModel.setCid(Long.parseLong(cidArray[i]));
			ContentModel contentModel=dwrMapper.getContentModelByCid(recommendModel);
			if(contentModel==null){
				flag=false;
				message="不能重复推荐内容!";
				break;
			}else{
				if(contentModel.getIsPrivate()!=0 || contentModel.getcStatus()==3 ){
					flag=false;
					message="请仔细检查你选中的内容,私密内容和屏蔽内容无法推送";
					break;
				}
			}
		}
		map.put("flag", flag);
		map.put("message", message);
		return map;
	}
	
	
	
	

	@Override
	public boolean insertUserIsRecommend(String uid,int type) {
		boolean flag=true;
		if (uid.endsWith(",")) {
			uid = uid.substring(0, uid.length() - 1);
		}
		String[] uidArray = uid.split(",");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("type", Integer.valueOf(type));
		for(int i=0;i<uidArray.length;i++){
			map.put("uid",uidArray[i]);
			UserModel userModel=dwrMapper.getUserModel(map);
			if(userModel!=null){
				flag=false;
				break;
			}
		}
		/*if(flag){
			for(int j=0;j<uidArray.length;j++){
				long uidJ=Long.parseLong(uidArray[j]);
				UserRecommendModel userRecommendModel=new UserRecommendModel();
				userRecommendModel.setUid(uidJ);
				userRecommendModel.setCreate_time(System.currentTimeMillis());
				userAdminMapper.insertUserreCommend(userRecommendModel);
				redisHotService.addRecommendUser(uidJ);
			}
		}*/
		return flag;
	}
	
	
	@Override
	public void insertRecommended(String uid, int type,String reason) {
		if (uid.endsWith(",")) {
			uid = uid.substring(0, uid.length() - 1);
		}
		String[] uidArray = uid.split(",");
		for(int j=0;j<uidArray.length;j++){
			long uidJ=Long.parseLong(uidArray[j]);
			UserRecommendModel userRecommendModel=new UserRecommendModel();
			userRecommendModel.setUid(uidJ);
			userRecommendModel.setType(type);
			userRecommendModel.setReason(reason);
			userRecommendModel.setCreate_time(System.currentTimeMillis());
			userAdminMapper.insertUserreCommend(userRecommendModel);
			redisHotService.addRecommendUser(userRecommendModel);
		}
	}





	@Override
	public String insertLikeContent(long cid,int addNum) {
		String message="";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("num", -1);
		List<Long> list=dwrMapper.queryUserListByLikeMj(cid);
		if(list==null || list.size()<=0){
			message="对不起，马甲用户已经分配完，请添加马甲用户";
		}else{
			for(int i=0;i<addNum;i++){
				Random random = new Random();
				int ram=random.nextInt(list.size());
				long uid = list.get(ram);
				LikeContentModel likeContentModel=new LikeContentModel();
				likeContentModel.setCid(cid);
				likeContentModel.setUid(uid);
				likeContentModel.setCreateTime(System.currentTimeMillis());
				try {
					dwrMapper.insertLikeContentModel(likeContentModel);
					dwrMapper.updateContentLikeNum(map);
					contentSynService.synContentLike(uid, cid);
					message="喜欢成功";
					list.remove(ram);
				} catch (Exception e) {
					e.printStackTrace();
					message="喜欢失败";
				}
			}
			
			redisTimelineService.refreshOptUsersCache(cid);
		}
		return message;
	}

	public void setDwrMapper(DwrMapper dwrMapper) {
		this.dwrMapper = dwrMapper;
	}
	public void setContentSynService(IContentSynService contentSynService) {
		this.contentSynService = contentSynService;
	}
	public void setUserAdminMapper(UserAdminMapper userAdminMapper) {
		this.userAdminMapper = userAdminMapper;
	}
	public void setRedisHotService(IRedisHotService redisHotService) {
		this.redisHotService = redisHotService;
	}
	public void setRedisTimelineService(IRedisTimelineService redisTimelineService) {
		this.redisTimelineService = redisTimelineService;
	}
}
