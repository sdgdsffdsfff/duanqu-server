package com.duanqu.manager.service.user;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.DateUtil;
import com.duanqu.common.dao.user.FriendMapper;
import com.duanqu.common.index.IIndexBuilder;
import com.duanqu.common.model.BindModel;
import com.duanqu.common.model.CommentModel;
import com.duanqu.common.model.FeedBackModel;
import com.duanqu.common.model.FriendModel;
import com.duanqu.common.model.IndexOpenUserModel;
import com.duanqu.common.model.MessageModel;
import com.duanqu.common.model.MessagePushModel;
import com.duanqu.common.model.OpenFriend;
import com.duanqu.common.model.SetUserModel;
import com.duanqu.common.model.UserAdminModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.model.UserRecommendModel;
import com.duanqu.common.share.IShareService;
import com.duanqu.common.submit.MContentSubmit;
import com.duanqu.common.submit.MFeedBackSubmit;
import com.duanqu.common.submit.MMessageSubmit;
import com.duanqu.common.submit.MPushHistorySubmit;
import com.duanqu.common.submit.MUserSubmit;
import com.duanqu.manager.dao.CommentMapper;
import com.duanqu.manager.dao.DwrMapper;
import com.duanqu.manager.dao.UserAdminMapper;
import com.duanqu.manager.submit.ManagerCommentSubmit;
import com.duanqu.manager.submit.ManagerMessageSubmit;
import com.duanqu.manager.submit.ManagerMjUserSubmit;
import com.duanqu.manager.submit.ManagerUserForm;
import com.duanqu.manager.submit.ManagerUserSubmit;
import com.duanqu.redis.service.comment.IRedisCommentService;
import com.duanqu.redis.service.hot.IRedisHotService;
import com.duanqu.redis.service.syn.comment.ICommentSynService;
import com.duanqu.redis.service.syn.message.IMessageSynService;
import com.duanqu.redis.service.syn.user.IUserSynService;
import com.duanqu.redis.service.user.IRedisRelationshipService;
import com.duanqu.redis.service.user.IRedisUserService;


public class ManagerUserServiceImpl implements IManagerUserService {
	Log logger = LogFactory.getLog(UserAdminMapper.class);
	private UserAdminMapper userAdminMapper;

	private CommentMapper commentMapper;
	
	private DwrMapper dwrMapper;

	private IRedisHotService redisHotService;

	private IRedisRelationshipService redisRelationshipService;

	IShareService shareService;

	IUserSynService userSynService;

	IMessageSynService messageSynService;

	IRedisUserService redisUserService;
	
	ICommentSynService commentSynService;
	
	IRedisCommentService redisCommentService;
	
    private static int pageSize = 100;
	
	FriendMapper friendMapper;
	
	IIndexBuilder indexBuilder;
	
	@Override
	public UserAdminModel checkUserAdimin(UserAdminModel userAdminModel) {
		return userAdminMapper.selectUserAdminModel(userAdminModel);

	}

	@Override
	public void inserHotUserList() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long todayTime = calendar.getTimeInMillis();
		int a = 24 * 60 * 60 * 1000;
		long weekTime=todayTime-a*7;
		List<Long> list = userAdminMapper.selectHotUserList(weekTime);
		
		List<SetUserModel> setlist=userAdminMapper.querySetUserModels();
		if(setlist!=null){
			for(SetUserModel setUserModel:setlist){
				int order=setUserModel.getOrderNum();
				if(order>list.size()){
					list.add(setUserModel.getUid());
				}else{
					list.add(order-1, setUserModel.getUid());
				}
			}
		}
		if(list.size()>302){
			list=list.subList(0, 302);
		}
		redisHotService.insertHotUser(list);
	}
	
	

	@Override
	public int queryUserMj(long num, int type) {
		List<Long> list=new ArrayList<Long>();
		int size=0;
		if(type==1){//内容可用马甲
			list=dwrMapper.queryUserListByLikeMj(num);
		}else{//粉丝可用马甲
			list=userAdminMapper.queryMjByJfs(num);
		}
		if(list!=null){
			size=list.size();
		}
		return size;
	}

	public void insertOpenFriend(long uid, List<OpenFriend> list, int openType) {
		List<OpenFriend> newList = new ArrayList<OpenFriend>();
		if (list != null) {
			for (Iterator<OpenFriend> iterator = list.iterator(); iterator
					.hasNext();) {
				OpenFriend openFriend = iterator.next();
				openFriend.setUid(uid);
				OpenFriend dBopenFriend = userAdminMapper
						.selectOpenFriend(openFriend);
				if (dBopenFriend == null) {
					try {
						userAdminMapper.inserOpenFriend(openFriend);
						newList.add(openFriend);
					} catch (Exception e) {
						logger.error("插入第三方好友数据出错！params=" + openFriend
								+ ";Message=" + e.getMessage());
					}
				}
			}
		}
		redisRelationshipService.insertNoMatchFriends(uid, newList, openType);
	}

	@Override
	public void insertMjUser(ManagerMjUserSubmit managerMjUserSubmit) {
		UserModel userModel = new UserModel();
		MultipartFile avatarUrl = managerMjUserSubmit.getAvatarUrl();
		MultipartFile backGroundUrl = managerMjUserSubmit.getBackgroundUrl();
		userModel.setUid(redisUserService.getUserId());// 从缓存获取用户主键
		userModel.setNickName(managerMjUserSubmit.getNickName());
		userModel.setSignature(managerMjUserSubmit.getSignature());
		userModel.setRoleId(4);
		userModel.setCreateTime(System.currentTimeMillis());
		try {
			String avatarUrlStr = AliyunUploadUtils.uploadAvatar(
					avatarUrl.getInputStream(), avatarUrl.getBytes().length,
					avatarUrl.getContentType());
			String backGroundStr = AliyunUploadUtils.uploadAvatar(
					backGroundUrl.getInputStream(),
					backGroundUrl.getBytes().length, backGroundUrl.getContentType());
			userModel.setAvatarUrl(avatarUrlStr);
			userModel.setBackgroundUrl(backGroundStr);
		} catch (Exception e) {
			logger.error("上传头像或者背景失败！Message=" + e.getMessage());
		}
		userAdminMapper.insertUserInfo(userModel);
		redisUserService.insertUser(userModel);// 插入缓存
	}

	@Override
	public void updateThfriend(Map<String, Object> map) {
		userAdminMapper.updateThfriend(map);
	}

	/**
	 * 定时器调度
	 */
	@Override
	public void getUserFriendList() {
		List<BindModel> list = userAdminMapper.queryBindModelList();
		if (list != null && list.size() > 0) {
			for (Iterator<BindModel> iterator = list.iterator(); iterator
					.hasNext();) {
				BindModel bindModel = iterator.next();
				int openType = bindModel.getOpenType();
				List<OpenFriend> listFriend;
				if (openType == 1) {
					listFriend = shareService.loadSinaFollows(
							bindModel.getOpenUid(), bindModel.getAccessToken());
				} else {
					listFriend = shareService.loadQQFollows(
							bindModel.getOpenUid(), bindModel.getAccessToken());
				}
				//插入新的第三方好友数据入数据库
				insertOpenFriend(bindModel.getUid(), listFriend, openType);
			}
		}
	}
	
	/**
	 * 由定时器调度
	 */
	
	@Override
	public void updateUser() {
		long count=userAdminMapper.queryUserModelsCount();
		long totalPage=0;
		int pageSize=1000;
		if((count % pageSize)>0){
			totalPage=count/pageSize+1;
		}else{
			totalPage=count/pageSize;
		}
		for(int i=1;i<=totalPage;i++){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("pageStart", (i-1)*pageSize);
			map.put("pageSize", pageSize);
			List<UserModel> userList = userAdminMapper.queryUserModels(map);
			for (UserModel user : userList){
				if (user != null && user.getUid()>0){
					//同步手机号码信息
					if (StringUtils.isNotBlank(user.getMobile())){
						synMobiles(user.getUid());
					}
					//同步第三方好友信息
					synOpenFriend(user.getUid());
				}
			}
		}
	}
	
	/**
	 * 匹配并同步手机号码
	 * @param uid
	 */
	private void synMobiles(long uid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_exectype", 1);
		map.put("p_functype", 2);
		map.put("p_uid",uid);
		updateThfriend(map);
		List<OpenFriend> listOpenList=userAdminMapper.queryMobiles();
		if(listOpenList!=null&&listOpenList.size()>0){
			try {
				redisRelationshipService.insertMatchedFriends(
						uid, listOpenList);
			} catch (Exception e) {
				logger.error("最新匹配成功数据插入缓存出错！params=" + uid
						+ ";Message=" + e.getMessage());
			}
			userAdminMapper.updateMobile();// 更新匹配数据为老数据
		}
	}
	
	/**
	 * 匹配并同步第三方平台好友数据
	 * @param uid
	 */
	private void synOpenFriend(long uid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_exectype", 1);
		map.put("p_functype", 1);
		map.put("p_uid", uid);
		updateThfriend(map);
		List<OpenFriend> listOpenFriends = userAdminMapper.queryOpenFriends();
		if (listOpenFriends != null && listOpenFriends.size() > 0) {
			try {
				redisRelationshipService.insertMatchedFriends(uid, listOpenFriends);
			} catch (Exception e) {
				logger.error("最新匹配成功数据插入缓存出错！params=" + uid
						+ ";Message=" + e.getMessage());
			}
			userAdminMapper.updateOpenFriend();// 更新匹配数据为老数据
		}
	}
	
	@Override
	public void duanquUpdateUserAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_exectype", 1);
		map.put("p_uid",Long.parseLong("0"));
		map.put("p_functype", 1);
		updateThfriend(map);//匹配第三平台
		map.put("p_functype", 2);
		updateThfriend(map);//匹配手机
		boolean flag=true;
		
		List<OpenFriend> listOpenList=userAdminMapper.queryMobiles();
		try {
			redisRelationshipService.batchInsertMatchFriends(listOpenList);
		} catch (Exception e) {
			logger.error("最新手机匹配成功数据插入缓存出错！Message=" + e.getMessage());
			flag=false;
		}
		if(flag){
			userAdminMapper.updateMobile();
		}
		List<OpenFriend> listOpenFriends = userAdminMapper.queryOpenFriends();
		try {
			redisRelationshipService.batchInsertMatchFriends(listOpenFriends);
			flag=true;
		} catch (Exception e) {
			logger.error("最新第三方平台匹配成功数据插入缓存出错！Message=" + e.getMessage());
			flag=false;
		}
		if(flag){
			userAdminMapper.updateOpenFriend();
		}
	}

	@Override
	public void getUserModeisList() {
		List<Long> list = userAdminMapper.queryMobilesList();
		if (list != null && list.size() > 0) {
			for (Long uidLong:list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("p_exectype", 1);
				map.put("p_functype", 2);
				map.put("p_uid",uidLong);
				updateThfriend(map);
				List<OpenFriend> listOpenList=userAdminMapper.queryMobiles();
				if(listOpenList!=null&&listOpenList.size()>0){
					try {
						redisRelationshipService.insertMatchedFriends(
								uidLong, listOpenList);
					} catch (Exception e) {
						logger.error("最新匹配成功数据插入缓存出错！params=" + uidLong
								+ ";Message=" + e.getMessage());
					}
					userAdminMapper.updateMobile();// 更新匹配数据为老数据
				}
				
				
			}
		}

	}

	@Override
	public void queryUserList(MUserSubmit mUserSubmit) {
		String userbind = mUserSubmit.getUserbind();
		/*long count;
		List<Map<String, Object>> list = null;
		if (userbind != null && userbind.equals("3")) {
			count = userAdminMapper.queryUserListMCount(mUserSubmit);
			mUserSubmit.computerTotalPage(count);
			list = userAdminMapper.queryUserListM(mUserSubmit);
		} else {
			count = userAdminMapper.queryUserListCount(mUserSubmit);
			mUserSubmit.computerTotalPage(count);
			Lis
			list = userAdminMapper.queryUserList(mUserSubmit);
		}*/
		long count=userAdminMapper.queryUserListCount(mUserSubmit);
		mUserSubmit.computerTotalPage(count);
		List<ManagerUserForm> objList=new ArrayList<ManagerUserForm>();
		if("nrs".equals(mUserSubmit.getPxtj())|| "fxs".equals(mUserSubmit.getPxtj())){
			objList=userAdminMapper.queryUserListNrs(mUserSubmit);
		}else{
			objList=userAdminMapper.queryUserList(mUserSubmit);
		}		
		List<ManagerUserForm> newList=new ArrayList<ManagerUserForm>();
		/*for(ManagerUserForm managerUserForm:objList){
			String mobile=managerUserForm.getMobile();
			if() 
		}*/
		

		mUserSubmit.setObjList(objList);
	}
	

	
	@Override
	public void queryTsUserList(MUserSubmit mUserSubmit) {
		long count=userAdminMapper.queryTsUserListCount(mUserSubmit);
		mUserSubmit.computerTotalPage(count);
		List<Map<String, Object>> list=userAdminMapper.queryTsUserList(mUserSubmit);
		mUserSubmit.setObjList(list);
	}

	@Override
	public void queryMjUserList(MUserSubmit mUserSubmit) {
		long count = userAdminMapper.queryMjUserListCount(mUserSubmit);
		mUserSubmit.computerTotalPage(count);
		List<ManagerUserForm> list = userAdminMapper
				.queryMjUserList(mUserSubmit);
		mUserSubmit.setObjList(list);
	}
	
	
	
	
	

	@Override
	public void deletePushMessage(MPushHistorySubmit pushHistorySubmit) {
		
		userAdminMapper.deleteMessagePush(pushHistorySubmit);
	}

	@Override
	public void queryPushMessageHistoryList(MPushHistorySubmit pushHistorySubmit) {
		long count=userAdminMapper.queryMessagePushHistoryListCount(pushHistorySubmit);
		pushHistorySubmit.computerTotalPage(count);
		List<MessagePushModel> list=userAdminMapper.queryMessagePushHistoryList(pushHistorySubmit);
		pushHistorySubmit.setObjList(list);
		
	}

	@Override
	public String insertFalseFriend(long uid,int addNum) {
		String message="";
		List<Long> list=userAdminMapper.queryMjByJfs(uid);
		Map<String, Object> map=new HashMap<String, Object>();
		FriendModel fmNew=new FriendModel();
		if(list==null || list.size()==0){
			message="对不起,马甲用户已经使用完毕,请添加马甲用户";
		}else{
			for(int i=0;i<addNum;i++){
				Random random = new Random();
				int ran=random.nextInt(list.size());
				long mjuid = list.get(ran);
				fmNew.setUid(mjuid);
				fmNew.setFid(uid);
				fmNew.setCreateTime(System.currentTimeMillis());
				fmNew.setIsTrue(0);
				FriendModel friendModel=new FriendModel();
				friendModel.setUid(uid);
				friendModel.setFid(mjuid);
				try {
					FriendModel fm=userAdminMapper.getFriendModel(friendModel);
					if(fm!=null){//说明当前用户关注了该马甲，他们之间属于好友关系
						fmNew.setIsFriend(1);
						map.put("uid", uid);
						map.put("num", 1);
						userAdminMapper.updateUserHys(map);//更新当前用户的好友数
						map.put("uid", mjuid);
						userAdminMapper.updateUserHys(map);//更新随机取到的马甲的好友数
					}else {
						fmNew.setIsFriend(0);
					}
					userAdminMapper.insertFalseFriend(fmNew);
					map.put("uid",mjuid);
					map.put("num", 1);
					userAdminMapper.updateUserGzs(map);//更新马甲的关注数
					map.put("uid", uid);
					userAdminMapper.updateUserJfss(map);//更新当前用户的假粉丝数
					message="增加成功";
					userSynService.synFollow(mjuid, uid);
					list.remove(ran);
				} catch (Exception e) {
					e.printStackTrace();
					message="增加失败";
				}
			}
		}
		return message;
	}

	@Override
	public void queryMessageList(MMessageSubmit mMessageSubmit) throws Exception {
		
		if (mMessageSubmit.getCreateTimeQ() != null
				&& mMessageSubmit.getCreateTimeQ() != "") {
			mMessageSubmit.setCreateTimeQL(mMessageSubmit.toLong(mMessageSubmit
					.getCreateTimeQ()));
		}
		if (mMessageSubmit.getCreateTimeZ() != null
				&& mMessageSubmit.getCreateTimeZ() != "") {
			mMessageSubmit.setCreateTimeZL(mMessageSubmit.toLong(mMessageSubmit
					.getCreateTimeZ()));
		}
		
		
		long count=userAdminMapper.queryMessageListCount(mMessageSubmit);
		mMessageSubmit.computerTotalPage(count);
		List<Map<String, Object>> list=userAdminMapper.queryMessageList(mMessageSubmit);
		mMessageSubmit.setObjList(list);
	}

	@Override
	public void queryMjCommentList(MUserSubmit mUserSubmit) {
		long count = commentMapper.queryMjCommentSubmitsCount(mUserSubmit);
		mUserSubmit.computerTotalPage(count);
		List<ManagerCommentSubmit> list = commentMapper
				.queryMjCommentSubmits(mUserSubmit);
		List<ManagerCommentSubmit> newList = new ArrayList<ManagerCommentSubmit>();
		if (list != null && list.size() > 0) {
			for (Iterator<ManagerCommentSubmit> iterator = list.iterator(); iterator
					.hasNext();) {
				ManagerCommentSubmit managerCommentSubmit = iterator.next();
				ManagerCommentSubmit child;
				UserModel userModel;
				long prientId = managerCommentSubmit.getPrientId();
				long replyUid=managerCommentSubmit.getReplyUid();
				if (prientId != 0) {
					child = commentMapper.getManagerCommentSubmit(prientId);
					managerCommentSubmit.setManagerCommentSubmit(child);
				}
				if(replyUid!=0){
					userModel=userAdminMapper.getUserModelByUid(replyUid);
					managerCommentSubmit.setReplyUser(userModel);
				}
				newList.add(managerCommentSubmit);
			}
		}
		mUserSubmit.setObjList(newList);
	}
	
	
	
	
	
	@Override
	public void queryDqjCommentList(MContentSubmit mContentSubmit) {
		long count = commentMapper.queryDqjCommentSubmitsCount(mContentSubmit);
		mContentSubmit.computerTotalPage(count);
		List<ManagerCommentSubmit> list = commentMapper
				.queryDqjCommentSubmits(mContentSubmit);
		List<ManagerCommentSubmit> newList = new ArrayList<ManagerCommentSubmit>();
		if (list != null && list.size() > 0) {
			for (Iterator<ManagerCommentSubmit> iterator = list.iterator(); iterator
					.hasNext();) {
				ManagerCommentSubmit managerCommentSubmit = iterator.next();
				ManagerCommentSubmit child;
				UserModel userModel;
				long prientId = managerCommentSubmit.getPrientId();
				long replyUid=managerCommentSubmit.getReplyUid();
				if (prientId != 0) {
					child = commentMapper.getManagerCommentSubmit(prientId);
					managerCommentSubmit.setManagerCommentSubmit(child);
				}
				if(replyUid!=0){
					userModel=userAdminMapper.getUserModelByUid(replyUid);
					managerCommentSubmit.setReplyUser(userModel);
				}
				newList.add(managerCommentSubmit);
			}
		}
		mContentSubmit.setObjList(newList);
	}

	@Override
	public void deleteContentComment(CommentModel commentModel) {
		long cid=commentModel.getCid();
		long rootId=commentModel.getRootId();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("cid", cid);
		int num=0;
		if(rootId==0){
			//删除主评论及所有子评论
			num=commentMapper.deleteAllContentComment(commentModel);
		}else {
			num=commentMapper.deleteContentComment(commentModel);
		}
		map.put("num", num);
		commentMapper.updateContentInfo(map);
	}

	@Override
	public void insertUserreCommend(MUserSubmit mUserSubmit) {
		List<Long> list = mUserSubmit.getUidList();
		for (Iterator<Long> iterator = list.iterator(); iterator.hasNext();) {
			UserRecommendModel userRecommendModel = new UserRecommendModel();
			long uid = iterator.next();
			userRecommendModel.setUid(uid);
			userRecommendModel.setCreate_time(System.currentTimeMillis());
			UserRecommendModel ur=userAdminMapper.getUserRecommend(uid);
			if(ur!=null){
				userAdminMapper.updateUserRecommend(userRecommendModel);
			}else{
				userAdminMapper.insertUserreCommend(userRecommendModel);	
			}
			redisHotService.addRecommendUser(userRecommendModel);
		}
	}
	
	
	

	@Override
	public void deleteUserRecommend(MUserSubmit mUserSubmit,int type) {
		long uid=Long.parseLong(mUserSubmit.getUid());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("uid",Long.valueOf(uid));
		map.put("type", Integer.valueOf(type));
		userAdminMapper.deleteUserRecommend(map);
		
		//王海华修改，达人和推荐用户方法为同一个
		UserRecommendModel userRecommendModel = new UserRecommendModel();
		userRecommendModel.setUid(uid);
		userRecommendModel.setType(type);
		redisHotService.deleteRecommendUser(userRecommendModel);
			
		
	}

	@Override
	public void insertReplyComment(CommentModel commentModel) {
		long cid=commentModel.getCid();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("num", -1);
		commentModel.setId(redisCommentService.getCommentId());
		commentModel.setCreateTime(System.currentTimeMillis());
		//commentModel.setCommentUrl("/0");
		if(commentModel.getRootId()==0){
			commentModel.setRootId(commentModel.getParentId());
		}
		commentSynService.synCommentAdd(commentModel);//插入redis
		commentMapper.insertContentComment(commentModel);//插入评论表
		commentMapper.updateContentInfo(map);//更新评论数
		commentMapper.updateCommentIsNew(commentModel.getParentId());//更新评论为旧评论
	}
	
	

	@Override
	public void updateComment(long uid) {
		userAdminMapper.updateComment(uid);
	}
	

	@Override
	public void updateCommentDqj(long cid) {
		userAdminMapper.updateCommentDqj(cid);
	}

	@Override
	public void insertMessage(ManagerMessageSubmit managerMessageSubmit) {
		String uid = managerMessageSubmit.getUid();
		int fslx=managerMessageSubmit.getFslx();
		int qflx=managerMessageSubmit.getQflx();
		long messageId=managerMessageSubmit.getMessageId();
		if(fslx==1){//回复私信
			MessageModel messageModel = new MessageModel();
			messageModel.setUid(1);
			messageModel.setMessageText(managerMessageSubmit
					.getMessageText());
			messageModel.setRecUid(Long.parseLong(uid));
			messageModel.setCreateTime(System.currentTimeMillis());
			messageModel.setType(1+"|"+Long.parseLong(uid));
			messageModel.setIsNew(1);
			userAdminMapper.insertUserMessage(messageModel);
			userAdminMapper.updateMessage(messageId);//更新回复的消息为已回复
			messageSynService.synMessageSend(messageModel);		
		}else if(fslx==2){//回复反馈信息
			MessageModel messageModel = new MessageModel();
			messageModel.setUid(1);
			messageModel.setMessageText(managerMessageSubmit
					.getMessageText());
			messageModel.setRecUid(Long.parseLong(uid));
			messageModel.setType(1+"|"+Long.parseLong(uid));
			messageModel.setCreateTime(System.currentTimeMillis());
			messageModel.setIsNew(1);
			userAdminMapper.insertUserMessage(messageModel);
			messageSynService.synMessageSend(messageModel);
			FeedBackModel feedBackModel=new FeedBackModel();
			feedBackModel.setId(managerMessageSubmit.getId());//反馈信息id
			feedBackModel.setIsCheck(1);
			userAdminMapper.updateFeedBackModel(feedBackModel);//更新该条反馈信息的状态	
		}else{	
			if(qflx==1){
				MessageModel messageModel=new MessageModel();
				messageModel.setCreateTime(System.currentTimeMillis());
				messageModel.setMessageText(managerMessageSubmit.getMessageText());
				
			//	userAdminMapper.insertSendAllMessage(messageModel);
				long count=userAdminMapper.queryUserModelsCount();
				long totalPage=0;
				int pageSize=200;
				if((count % pageSize)>0){
					totalPage=count/pageSize+1;
				}else{
					totalPage=count/pageSize;
				}
				/*for(int i=1;i<=totalPage;i++){
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("pageStart", (i-1)*pageSize);
					map.put("pageSize", pageSize);
					List<UserModel> userList = userAdminMapper.queryUserModels(map);
					for (UserModel user : userList){
						MessageModel mm = new MessageModel();
						mm.setUid(1);
						mm.setMessageText(managerMessageSubmit
								.getMessageText());
						mm.setRecUid(user.getUid());	
						mm.setCreateTime(System.currentTimeMillis());
						messageSynService.synMessageSend(mm);
					}
				}*/
				ThreadSendMessage threadSendMessage=new ThreadSendMessage(totalPage,userAdminMapper,messageSynService,managerMessageSubmit.getMessageText());
				threadSendMessage.send();	
			}else if(qflx==3){
				long count=userAdminMapper.queryUserModelsCount();
				long totalPage=0;
				int pageSize=1000;
				if((count % pageSize)>0){
					totalPage=count/pageSize+1;
				}else{
					totalPage=count/pageSize;
				}
				/*ThreadSendPush threadSendPush=new ThreadSendPush(totalPage,userAdminMapper,messageSynService,managerMessageSubmit.getMessageText());
				threadSendPush.send();	*/
				
			}
			else{
				if (uid.endsWith(",")) {
					uid = uid.substring(0, uid.length() - 1);
				}
				String[] uidArray = uid.split(",");
				if (uidArray != null && uidArray.length > 0) {
					for (int i = 0; i < uidArray.length; i++) {
						MessageModel messageModel = new MessageModel();
						messageModel.setUid(1);
						messageModel.setMessageText(managerMessageSubmit
								.getMessageText());
						messageModel.setType(1+"|"+Long.parseLong(uidArray[i]));
						messageModel.setRecUid(Long.parseLong(uidArray[i]));
						messageModel.setCreateTime(System.currentTimeMillis());
						messageModel.setIsNew(1);
						userAdminMapper.insertUserMessage(messageModel);
						messageSynService.synMessageSend(messageModel);
					}
				}
				
			}
		}
		
	}
	
	
	

	@Override
	public void insertMessagePush(String type, int tslx, String messageText,
			String innerParam, String createTime) {
		MessagePushModel messagePushModel=new MessagePushModel();
		if(!"".equals(createTime)){
			try {
				Date timeDate=DateUtil.parse(createTime, "yyyy-MM-dd HH:mm:ss");
				messagePushModel.setCreateTime(timeDate.getTime());
			} catch (ParseException e) {
				logger.error("日期类型转换错误："+e.getMessage());
			}
		}else{
			messagePushModel.setCreateTime(System.currentTimeMillis());
		}
		if(tslx==1){
			messagePushModel.setIsShow(1);
			long count=userAdminMapper.queryUserModelsCount();
			long totalPage=0;
			int pageSize=1000;
			if((count % pageSize)>0){
				totalPage=count/pageSize+1;
			}else{
				totalPage=count/pageSize;
			}
			ThreadSendPush threadSendPush=new ThreadSendPush(totalPage,userAdminMapper,messageSynService,messageText,innerParam,type);
			threadSendPush.send();	
		}else{
			messagePushModel.setIsShow(0);
		}
		messagePushModel.setInnerParam(innerParam);
		messagePushModel.setType(type);
		messagePushModel.setMessageText(messageText);
		userAdminMapper.insertMessagePush(messagePushModel);
	}
	
	
    
	@Override
	public void duanquMessagePushFromDb() {
		long count=userAdminMapper.queryUserModelsCount();
		long totalPage=0;
		int pageSize=1000;
		if((count % pageSize)>0){
			totalPage=count/pageSize+1;
		}else{
			totalPage=count/pageSize;
		}
		List<MessagePushModel> list=userAdminMapper.queryMessagePushList(System.currentTimeMillis());
		for(MessagePushModel messagePushModel:list){
			ThreadSendPush threadSendPush=new ThreadSendPush(totalPage,userAdminMapper,messageSynService,messagePushModel.getMessageText(),messagePushModel.getInnerParam(),messagePushModel.getType());
			threadSendPush.send();	
			userAdminMapper.updateMessagePush(messagePushModel.getId());
		}
	}
	

	@Override
	public void updateUserAuthentication(long uid,int flag, String messageText) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("authenticationReason", messageText);
		map.put("flag",flag);
		userAdminMapper.updateUserAuthentication(map);
		MessageModel messageModel = new MessageModel();
		messageModel.setUid(1);
		if(flag==1){
			messageModel.setMessageText("您已成为趣拍认证用户!");
		}else{
			messageModel.setMessageText("您已被取消认证资格，如有疑问请私信趣拍君");		
		}
		messageModel.setRecUid(uid);
		messageModel.setType(1+"|"+uid);
		messageModel.setCreateTime(System.currentTimeMillis());
		messageModel.setIsNew(1);
		userAdminMapper.insertUserMessage(messageModel);
		messageSynService.synMessageSend(messageModel);
		redisUserService.updateUserTalentInfo(uid, flag, messageText);
		
	}
	
	

	@Override
	public void updateAuthenticationReason(long uid, String messageText) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("authenticationReason", messageText);
		map.put("flag",1);
		userAdminMapper.updateUserAuthentication(map);
		redisUserService.updateUserTalentInfo(uid, 1, messageText);
	}

	@Override
	public Map<String, Object> insertMessageDetail(long uid, String messageText) {
		Map<String, Object> map=new HashMap<String, Object>();
		MessageModel messageModel=new MessageModel();
		long time=System.currentTimeMillis();
		String timeString=DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm:ss");
		messageModel.setUid(1);
		messageModel.setRecUid(uid);
		messageModel.setCreateTime(time);
		messageModel.setMessageText(messageText);
		messageModel.setType(1+"|"+uid);
		userAdminMapper.insertUserMessage(messageModel);
		messageSynService.synMessageSend(messageModel);	
		map.put("id", messageModel.getId());
		map.put("time", timeString);
		return map;
		
	}

	@Override
	public void buildIndex() {
		
		
		buildOpenUser();
		buildMobile();
		
	}
	
	

	@Override
	public String getRecommendReason(UserRecommendModel userRecommendModel) {
		return userAdminMapper.getRecommendReason(userRecommendModel);
	}

	@Override
	public void updateRecommendReason(UserRecommendModel userRecommendModel) {
		userAdminMapper.updateRecommendReason(userRecommendModel);
		redisHotService.updateRecommendReason(userRecommendModel);
		
		
	}

	@Override
	public void updateUserJy(ManagerUserSubmit managerUserSubmit) {
		try {
			managerUserSubmit.setBanEndtime(managerUserSubmit
					.toLong(managerUserSubmit.getBanEndtimeStr()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		userAdminMapper.updateUserJy(managerUserSubmit);
		userSynService.synUserForbid(managerUserSubmit.getUid());// 插入缓存
	}
	

	@Override
	public void deleteMessage(ManagerMessageSubmit managerMessageSubmit) {
		long uid=Long.parseLong(managerMessageSubmit.getUid());
		if(uid==1){//短趣君作为发送者
				userAdminMapper.deleteMessage(managerMessageSubmit.getMessageId());
		}else{
			userAdminMapper.deleteMessageRec(managerMessageSubmit.getMessageId());
		}
	}
	@Override
	public void queryFeedBackList(MFeedBackSubmit mFeedBackSubmit) {
		long count=userAdminMapper.queryFeedBackModelsCount(mFeedBackSubmit);
		mFeedBackSubmit.computerTotalPage(count);
		List<Map<String, Object>> objList=userAdminMapper.queryFeedBackModels(mFeedBackSubmit);
		mFeedBackSubmit.setObjList(objList);	
	}

    /**
     * 
     */
	@Override
	public void updateFeedBack(FeedBackModel feedBackModel) {
		userAdminMapper.updateFeedBackModel(feedBackModel);
	}
    
	@Override
	public void updateUserNormal(long uid) {
		userAdminMapper.updateUserNormal(uid);
		userSynService.synUserUnforbid(uid);
	}
	
	

	@Override
	public void updateUserStatus(long uid, int status) {
		UserModel userModel=new UserModel();
		userModel.setUid(uid);
		userModel.setStatus(status);
		userAdminMapper.updateUserStatus(userModel);
	}

	@Override
	public void updateUserNormalAll() {
		long time=System.currentTimeMillis();
		List<Long> list=userAdminMapper.queryUserJyList(time);
		for(Long uid:list){
			userSynService.synUserUnforbid(uid);
		}
		try {
			userAdminMapper.updateUserNormalAll(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	@Override
	public List<Map<String, Object>> queryMessageDetail(
			MessageModel messageModel) {
		long uid=messageModel.getUid();
		long rec_uid=messageModel.getRecUid();
		if(rec_uid>uid){
			messageModel.setUid(rec_uid);
		}
		return userAdminMapper.queryMessageDetail(messageModel);
	}
	
	
	

	@Override
	public SetUserModel getUserModel(long uid) {
		
		return userAdminMapper.getSetUserModel(uid);
	}

	@Override
	public void insertSetUser(long uid, int order_num) {
		SetUserModel setUserModel=new SetUserModel();
		setUserModel.setUid(uid);
		setUserModel.setOrderNum(order_num);
		setUserModel.setCreateTime(System.currentTimeMillis());
		SetUserModel setUserModel2=userAdminMapper.getSetUserModel(uid);
		if(setUserModel2==null){
			userAdminMapper.insertSetUser(setUserModel);
		}else{
			userAdminMapper.updateSetUser(setUserModel);
		}
			
	}

	@Override
	public List<SetUserModel> queryUserModels() {
		return userAdminMapper.querySetUserModels();
	}

	@Override
	public void queryTsHotUser(MUserSubmit mUserSubmit) {
		long count=userAdminMapper.queryTsHotUserListCount(mUserSubmit);
		mUserSubmit.computerTotalPage(count);
		List<Map<String, Object>> list=userAdminMapper.queryTsHotUserList(mUserSubmit);
		mUserSubmit.setObjList(list);
	}
	
	
	/**
	 * 索引第三方平台好友
	 */
	public void buildOpenUser(){
		int count = friendMapper.queryOpenFriendListCount();
		int pageCount = (count - 1) / pageSize + 1;
		System.out.println("第三方平台数据，总共有 " + pageCount + " 页数据需要索引");
		for (int i = 1;i<= pageCount;i++){
			System.out.println("正在索引第 "+i+" 页数据！");
			List<OpenFriend> friends = friendMapper.queryOpenFriendList((i - 1)*pageSize, pageSize);
			for (OpenFriend friend:friends){
				IndexOpenUserModel indexOpenUser = new IndexOpenUserModel();
				indexOpenUser.setId(friend.getOpenUserId()+"|"+friend.getUid());
				indexOpenUser.setOpenNickName(friend.getOpenNickName());
				indexOpenUser.setOpenType(friend.getOpenType());
				indexOpenUser.setOpenUserId(friend.getOpenUserId());
				indexOpenUser.setUid(friend.getUid());
				try{
					System.out.print("|");
					indexBuilder.buildOpenUserIndex(indexOpenUser);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			System.out.println("");
		}
	}
	
	
	/**
	 * 索引通讯录
	 */
	public void buildMobile(){
		int count = friendMapper.queryUserMobilesListCount();
		int pageCount = (count - 1) / pageSize + 1;
		System.out.println("通讯录数据，总共有 "+pageCount+" 页数据需要索引");
		for (int i = 1;i<= pageCount;i++){
			System.out.println("正在索引第 "+i+" 页数据！");
			List<OpenFriend> friends = friendMapper.queryUserMobilesList((i - 1)*pageSize, pageSize);
			for (OpenFriend friend:friends){
				IndexOpenUserModel indexOpenUser = new IndexOpenUserModel();
				indexOpenUser.setId(friend.getOpenUserId()+"|"+friend.getUid());
				indexOpenUser.setOpenNickName(friend.getOpenNickName());
				indexOpenUser.setOpenType(friend.getOpenType());
				indexOpenUser.setOpenUserId(friend.getOpenUserId());
				indexOpenUser.setUid(friend.getUid());
				try{
					System.out.print("|");
					indexBuilder.buildOpenUserIndex(indexOpenUser);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			System.out.println("");
		}
	}
	
	
	

	@Override
	public void deleteTsHotUser(long uid) {
		userAdminMapper.deleteTsHotUser(uid);
		
	}
	

	@Override
	public String getAuthenticationReason(long uid) {
		
		return userAdminMapper.getAuthenticationReason(uid);
	}

	@Override
	public long updateMessageIsOld(MessageModel messageModel) {
		long uid=messageModel.getUid();
		long rec_uid=messageModel.getRecUid();
		if(rec_uid>uid){
			messageModel.setUid(rec_uid);
			uid=rec_uid;
		}
		userAdminMapper.updateMessageIsOld(messageModel);
		return uid;
	}

	public UserAdminMapper getUserAdminMapper() {
		return userAdminMapper;
	}

	public void setUserAdminMapper(UserAdminMapper userAdminMapper) {
		this.userAdminMapper = userAdminMapper;
	}

	public IRedisHotService getRedisHotService() {
		return redisHotService;
	}

	public void setRedisHotService(IRedisHotService redisHotService) {
		this.redisHotService = redisHotService;
	}

	public IShareService getShareService() {
		return shareService;
	}

	public void setShareService(IShareService shareService) {
		this.shareService = shareService;
	}

	public IRedisRelationshipService getRedisRelationshipService() {
		return redisRelationshipService;
	}

	public void setRedisRelationshipService(
			IRedisRelationshipService redisRelationshipService) {
		this.redisRelationshipService = redisRelationshipService;
	}

	public IUserSynService getUserSynService() {
		return userSynService;
	}

	public void setUserSynService(IUserSynService userSynService) {
		this.userSynService = userSynService;
	}

	public IMessageSynService getMessageSynService() {
		return messageSynService;
	}

	public void setMessageSynService(IMessageSynService messageSynService) {
		this.messageSynService = messageSynService;
	}

	public CommentMapper getCommentMapper() {
		return commentMapper;
	}

	public void setCommentMapper(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	public IRedisUserService getRedisUserService() {
		return redisUserService;
	}

	public void setRedisUserService(IRedisUserService redisUserService) {
		this.redisUserService = redisUserService;
	}

	public ICommentSynService getCommentSynService() {
		return commentSynService;
	}

	public void setCommentSynService(ICommentSynService commentSynService) {
		this.commentSynService = commentSynService;
	}

	public IRedisCommentService getRedisCommentService() {
		return redisCommentService;
	}

	public void setRedisCommentService(IRedisCommentService redisCommentService) {
		this.redisCommentService = redisCommentService;
	}

	public void setDwrMapper(DwrMapper dwrMapper) {
		this.dwrMapper = dwrMapper;
	}

	public void setFriendMapper(FriendMapper friendMapper) {
		this.friendMapper = friendMapper;
	}

	public void setIndexBuilder(IIndexBuilder indexBuilder) {
		this.indexBuilder = indexBuilder;
	}
}
